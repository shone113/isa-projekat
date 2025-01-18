package rs.ac.uns.ftn.informatika.rest.service;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Role;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    BloomFilterService bloomFilterService;

    @Transactional
    public User register(User user) {
        try {
            Thread.sleep(3000);

            if (bloomFilterService.mightContain(user.getEmail())) {
                throw new Exception("Username might already exist. Please try another.");
            }
//            Thread.sleep(500);
            if(!user.isValid())
                throw new Exception("User is not valid");
            List<Role> roles = roleService.findByName("ROLE_USER");
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registerUser = userRepository.save(user);
            bloomFilterService.add(registerUser.getEmail());
            return registerUser;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findAll(Pageable page) { return userRepository.findAll(page); }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findRegistratedUsers(){
        List<User> users = userRepository.findAll();
        Role role = new Role();
        List<User> registeredUsers = users.stream()
                .filter(user -> user.getAuthorities() != role) /// provjeriti nisam siguran da li ce ovo raditi uopste
                .collect(Collectors.toList());
        return registeredUsers;
    }

    public List<User> filterUsers(String name, String surname, String email, Integer minPostsRange, Integer maxPostsRange) {
        return userRepository.filterUsers(name, surname, email, minPostsRange, maxPostsRange);
    }


    public Page<User> filterUsersPaged(Pageable page, String name, String surname, String email, Integer minPostsRange, Integer maxPostsRange) {
        return userRepository.filterUsersPaged(page, name, surname, email, minPostsRange, maxPostsRange);
    }

    public void setActivationToken(User user, String token) {
        try{
            user.setActivationToken(token);
            userRepository.save(user);
            mailService.sendNotificaitionAsync(user);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean activateAccount(String token) {
        User user = userRepository.getUsersByActivationToken(token);
        if(user == null)
            return false;
        user.setActivationToken(null);
        userRepository.save(user);
        return true;
    }

    public List<User> getSortedByFollowingCountAsc(){
        return userRepository.getSortedByFollowingCountAsc();
    }
    public Page<User> getSortedByFollowingCountAscPaged(Pageable page) { return userRepository.getSortedByFollowingCountAscPaged(page); }

    public List<User> getSortedByFollowingCountDesc(){
        return userRepository.getSortedByFollowingCountDesc();
    }
    public Page<User> getSortedByFollowingCountDescPaged(Pageable page) { return userRepository.getSortedByFollowingCountDescPaged(page); }

    public List<User> getSortedByEmailAsc(){
        return userRepository.getSortedByEmailAsc();
    }
    public Page<User> getSortedByEmailAscPaged(Pageable page) { return userRepository.getSortedByEmailAscPaged(page); }

    public List<User> getSortedByEmailDesc(){
        return userRepository.getSortedByEmailDesc();
    }
    public Page<User> getSortedByEmailDescPaged(Pageable page) { return userRepository.getSortedByEmailDescPaged(page); }

    @RateLimiter(name = "premium", fallbackMethod = "standardFallback")
    public User getByEmail(String email) { return userRepository.getUserByEmail(email); }

    public User standardFallback(RequestNotPermitted rnp) {
        System.out.println("Prevazidjen broj poziva u ogranicenom vremenskom intervalu");
        throw rnp;
    }
    public List<User> getAllUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        return userRepository.getAllUsers(pageable);
    }

    public User updateUser(User user) {
        User oldUser = userRepository.findById(user.getId()).orElse(null);
        if(oldUser == null)
            return null;
        List<Role> roles = roleService.findByName("ROLE_USER");
        user.setRoles(roles);
        if(!oldUser.getPassword().equals(user.getPassword()))
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
