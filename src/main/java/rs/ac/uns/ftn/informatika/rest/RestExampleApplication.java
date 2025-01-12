package rs.ac.uns.ftn.informatika.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableScheduling
@EnableTransactionManagement // Ovo omogućava podršku za transakcije
public class RestExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestExampleApplication.class, args);
	}

}
