package rs.ac.uns.ftn.informatika.rest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.rest.service.UserService;
import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Testing {
    @Autowired
    UserService userService;
    @Autowired
    IUserRepository userRepository;


    @Test
    @Transactional
    public void testLockingWorks() {
        System.out.println("Fetching user...");
        User user = userRepository.findUserByID(4);
        System.out.println("User locked: " + user.getId());

        try {
            Thread.sleep(10000); // Simulacija duge transakcije
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Transaction ending...");
    }
}
