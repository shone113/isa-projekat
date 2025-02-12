package rs.ac.uns.ftn.informatika.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Follow {

    @Autowired
    UserService userService;
    @Autowired
    IUserRepository userRepository;

    @Test
    public void testConcurrentRegistration() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

//        CountDownLatch latch = new CountDownLatch(1);

        Future<?> future1 = executor.submit(() -> {
            try {
                System.out.println("Thread 1 started");
                System.out.println("Thread 1 started");
                userRepository.findUserByID(4); // PRVI THREAD - zaključava red
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Future<?> future2 = executor.submit(() -> {
            try {
                Thread.sleep(1000); // Čekaj da prvi thread zaključa red
                System.out.println("Thread 2 started");
                userRepository.findUserByID(4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Provera da li drugi thread baca PessimisticLockingFailureException
         assertThrows(PessimisticLockingFailureException.class, () -> {
            userRepository.findUserByID(4);
        });

        System.out.println("Test je uspešno pao zbog Pessimistic Lock-a!");

    }

}
