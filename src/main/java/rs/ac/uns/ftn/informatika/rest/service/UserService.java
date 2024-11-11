package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.ActivationCode;
import rs.ac.uns.ftn.informatika.rest.domain.Role;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.ActivationCodeRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

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
    private ActivationCodeRepository activationCodeRepository;

    public User register(User user) {
        try {
            if(!user.isValid())
                throw new Exception("User is not valid");
            List<Role> roles = roleService.findByName("ROLE_USER");
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registerUser = userRepository.save(user);
            //activationCodeRepository.save(mailService.sendNotificaitionAsync(registerUser));
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

    public User activateAccount(String code, String email) {
        ActivationCode activationCode = activationCodeRepository.getActivationCodesByEmail(email);
        if(!code.equals(activationCode.getCode()) || email.isEmpty() || email == null)
            return null;
        User user = userRepository.getUserByEmail(email);
        activationCodeRepository.deleteById(activationCode.getId());
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.getUserByEmail(email);
//        if(user == null || user.getRole() != User.Role.AUTHENTICATED_USER)
//            return null;
//        if(!password.equals(user.getPassword()))
//            return null;
        return user;
    }

    public void sendActivateCode(String email) {
        User user = userRepository.getUserByEmail(email);
        if(user == null)
            return;
        ActivationCode activationCode = activationCodeRepository.getActivationCodesByEmail(email);
        if(activationCode == null)
            return;
        activationCodeRepository.deleteById(activationCode.getId());
        try {
            activationCodeRepository.save(mailService.sendNotificaitionAsync(user));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore the interrupt flag
            return;
        }
    }
    public List<User> getSortedByFollowingCountAsc(){
        return userRepository.getSortedByFollowingCountAsc();
    }

    public List<User> getSortedByFollowingCountDesc(){
        return userRepository.getSortedByFollowingCountDesc();
    }

    public List<User> getSortedByEmailAsc(){
        return userRepository.getSortedByEmailAsc();
    }

    public List<User> getSortedByEmailDesc(){
        return userRepository.getSortedByEmailDesc();
    }

    public User getByEmail(String email) { return userRepository.getUserByEmail(email); }
}
