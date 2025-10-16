package rs.ac.uns.ftn.informatika.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/*
 * 
 * Za pokretanje primera potrebno je instalirati RabbitMQ - https://www.rabbitmq.com/download.html
 */
@SpringBootApplication
@Import(rs.ac.uns.ftn.informatika.config.RabbitConfigConsumer.class)
public class RabbitmqConsumerExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqConsumerExampleApplication.class, args);
	}
}
