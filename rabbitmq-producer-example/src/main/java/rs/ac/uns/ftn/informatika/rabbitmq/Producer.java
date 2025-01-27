package rs.ac.uns.ftn.informatika.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplemq.client.HttpMessageQueueClient;
import com.simplemq.client.MessageQueueClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Producer {
	
	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendTo(String routingkey, Organization message){
		log.info("Sending> ... Message=[ " + message.getName() + " ] RoutingKey=[" + routingkey + "]");
		this.rabbitTemplate.convertAndSend(routingkey, message);
	}

	public void sendToSMQ(String routingkey, Organization message){
		log.info("Sending> ... Message=[" + message.getName() + "] RoutingKey=[" + routingkey + "]");
		MessageQueueClient messageQueueClient = new HttpMessageQueueClient("http://localhost:8090/api/simplemq");
		messageQueueClient.sendMessage(routingkey, message);
	}
}
