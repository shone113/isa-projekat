package rs.ac.uns.ftn.informatika.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendTo(String routingkey, Organization message){
		log.info("Sending> ... Message=[ " + message.getName() + " ] RoutingKey=[" + routingkey + "]");
		this.rabbitTemplate.convertAndSend(routingkey, message);
	}

}
