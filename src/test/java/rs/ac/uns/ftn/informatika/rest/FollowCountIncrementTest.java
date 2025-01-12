package rs.ac.uns.ftn.informatika.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FollowCountIncrementTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Test(expected = PessimisticLockingFailureException.class)
    public void testPessimisticLockingScenario() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
//                profileService.followProfile(4, 2); // izvrsavanje transakcione metode traje oko 200 milisekundi
                userService.findById(1);
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(200); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                /*
                 * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
                 * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
                 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
                 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
                 * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
                 */
//                profileService.followProfile(4, 3);
                userService.findById(1);
            }
        });
        try {
            future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
