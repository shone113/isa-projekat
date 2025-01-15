package rs.ac.uns.ftn.informatika.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;
import rs.ac.uns.ftn.informatika.rest.service.RabbitCareOrganizationService;

@Component
public class RabbitCareOrganization {
    private static final Logger logger = LoggerFactory.getLogger(RabbitCareOrganization.class);
    @Autowired
    private RabbitCareOrganizationService rabbitCareOrganizationService;
    @RabbitListener(queues = "${myqueue}")
    private void handler(Organization object){
        logger.info("Consumer>: [name: " + object.getName() + ", longitude: " + object.getLongitude() + ", latitude: " + object.getLatitude() + "]");
        rabbitCareOrganizationService.save(object);
    }
}
