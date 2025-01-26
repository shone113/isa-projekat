package rs.ac.uns.ftn.informatika.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;
import rs.ac.uns.ftn.informatika.rest.service.RabbitCareOrganizationService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class RabbitCareOrganization {
    private static final Logger logger = LoggerFactory.getLogger(RabbitCareOrganization.class);

    @Autowired
    private RabbitCareOrganizationService rabbitCareOrganizationService;

    @Value("${smq.queue}")
    private String queue;

    @RabbitListener(queues = "${myqueue}")
    private void handler(Organization object){
        logger.info("Consumer>: [name: " + object.getName() + ", longitude: " + object.getLongitude() + ", latitude: " + object.getLatitude() + "]");
        rabbitCareOrganizationService.save(object);
    }

    @Scheduled(cron = "${smq.cron}")
    private void smqHandler(){
        try {
            URL url = new URL("http://localhost:8090/api/simplemq/consume?queue=" + queue);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
//                        System.out.println("Message received: " + line);
                        logger.info("Consumer>: [name: " + line + "]");
                        ObjectMapper objectMapper = new ObjectMapper();

                        // Pretvori JSON string u objekat
                        Organization organization = objectMapper.readValue(line, Organization.class);
                        rabbitCareOrganizationService.save(organization);
                    }
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("SimpleMQ is not working!");
        }
    }
}
