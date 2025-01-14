package rs.ac.uns.ftn.informatika.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestExampleApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	IUserRepository userRepository;

	@Test
	public void testConcurrentRegistration() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		CountDownLatch latch = new CountDownLatch(1);

		Future<?> future1 = executor.submit(() -> {
			try {
				System.out.println("Thread 1 started");
				latch.await(); // Čeka da oba zadatka budu spremna
				User user = new User();
				user.setEmail("test@test.com");
				user.setPassword("password");
				userService.register(user);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});

		Future<?> future2 = executor.submit(() -> {
			try {
				System.out.println("Thread 2 started");
				latch.await(); // Čeka da oba zadatka budu spremna
				User user = new User();
				user.setEmail("test@test.com");
				user.setPassword("password");
				userService.register(user);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});

		// Oslobađanje niti da počnu skoro istovremeno
		latch.countDown();

		// Čekanje završetka niti
		executor.shutdown();
		executor.awaitTermination(10, TimeUnit.SECONDS);

		// Provera rezultata
		// Na primer, proverite broj korisnika u bazi sa istim emailom
		long count = userRepository.countUsersByEmail("test@test.com");
		assertEquals(1, count, "Samo jedan korisnik bi trebalo da bude registrovan!");

		// Takođe možete proveriti grešku za drugi pokušaj ako vaša aplikacija to podržava
		assertThrows(Exception.class, () -> {
			User duplicateUser = new User();
			duplicateUser.setEmail("test@test.com");
			duplicateUser.setPassword("password");
			userService.register(duplicateUser);
		}, "Drugi pokušaj registracije bi trebalo da izazove grešku!");
	}

}
