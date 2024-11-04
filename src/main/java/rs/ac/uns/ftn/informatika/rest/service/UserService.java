package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    public User register(User user) {
        try {
            return userRepository.save(user);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
