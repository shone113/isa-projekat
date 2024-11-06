package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.ActivationCode;
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
    private ActivationCodeRepository activationCodeRepository;

    public User register(User user) {
        try {
            if(!user.isValid())
                throw new Exception("User is not valid");
            User registerUser = userRepository.save(user);
            activationCodeRepository.save(mailService.sendNotificaitionAsync(registerUser));
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

    public List<User> findRegistratedUsers(){
        List<User> users = userRepository.findAll();
        List<User> registeredUsers = users.stream()
                .filter(user -> user.getRole() != User.Role.ADMINISTRATOR)
                .collect(Collectors.toList());
        return registeredUsers;
    }

    public List<User> filterUsers(String name) {
        return userRepository.filterUsers(name);
    }

    public User activateAccount(String code, String email) {
        ActivationCode activationCode = activationCodeRepository.getActivationCodesByEmail(email);
        if(!code.equals(activationCode.getCode()) || email.isEmpty() || email == null)
            return null;
        User user = userRepository.getUserByEmail(email);
        user.setRole(User.Role.AUTHENTICATED_USER);
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
}
