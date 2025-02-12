package rs.ac.uns.ftn.informatika.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepositoryImpl;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import javax.persistence.LockTimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowCountIncrementTest {

    private static final Logger logger = LoggerFactory.getLogger(FollowCountIncrementTest.class);

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private IUserRepositoryImpl userRepositoryImpl;

    @Autowired
    private IUserRepository userRepository;
    @Test(expected = PessimisticLockingFailureException.class)
    public void testPessimisticLockingScenario() throws Throwable {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> future1 = executor.submit(() -> {
            logger.info("Startovan Thread 1");

            try {
                Thread.sleep(500); // Smanji sleep kako bi se transakcije preklopile
            } catch (LockTimeoutException e) {
                throw new PessimisticLockingFailureException("Could not obtain lock on row", e);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread 1 je prekinut", e);
            }
            logger.info("Thread 1 pokušava da zaključa red za User sa ID: 4");
//            userRepository.findByIdWithLock(4); // Prva transakcija
            profileService.followProfile(4, 2);
            logger.info("Thread 1 završio sa zaključavanjem");
        });

        Future<?> future2 = executor.submit(() -> {
            logger.info("Startovan Thread 2");
            try {
                Thread.sleep(500); // Smanji sleep kako bi se transakcije preklopile
            }catch (LockTimeoutException e) {
                throw new PessimisticLockingFailureException("Could not obtain lock on row", e);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread 2 je prekinut", e);
            }
            logger.info("Thread 2 pokušava da zaključa red za User sa ID: 4");
//            userRepository.findByIdWithLock(4); // Druga transakcija, isti red
            profileService.followProfile(4, 3);
            logger.info("Thread 2 završio sa zaključavanjem");
        });

        try {
            future2.get(); // Čekamo da se drugi thread završi
        }catch (LockTimeoutException e) {
            throw new PessimisticLockingFailureException("Could not obtain lock on row", e);
        }
        catch (ExecutionException e) {
            logger.error("Exception from thread", e);
            throw e.getCause(); // Očekujemo PessimisticLockingFailureException
        } catch (InterruptedException e) {
            logger.error("Test je prekinut", e);
            e.printStackTrace();
        }

        executor.shutdown();
    }
}