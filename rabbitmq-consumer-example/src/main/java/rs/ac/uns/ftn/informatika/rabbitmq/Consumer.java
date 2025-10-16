package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.informatika.dto.AdPostMessageDTO;

@Component
public class Consumer {

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);

	@RabbitListener(queues = "#{autoDeleteQueue.name}")
	public void handler(AdPostMessageDTO message){
		log.info("Agencija primila objavu: {} | @{} | {}",
				message.getDescription(),
				message.getUsername(),
				message.getPublishingDate());
	}
}
