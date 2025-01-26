package rs.ac.uns.ftn.informatika.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
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
		try {
			URL url = new URL("http://localhost:8090/api/simplemq/produce?queue="+routingkey);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setDoOutput(true);

			ObjectMapper objectMapper = new ObjectMapper();

			// Pretvori u JSON string
			String jsonMessage = objectMapper.writeValueAsString(message);

			System.out.println("JSON String: " + jsonMessage);

			try (OutputStream os = connection.getOutputStream()) {
				os.write(jsonMessage.getBytes());
				os.flush();
			}

			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
