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


//    @RateLimiter(name = "premium", fallbackMethod = "standardFallback")
    public User getByEmail(String email) { return userRepository.getUserByEmail(email); }


}
