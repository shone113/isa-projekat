package rs.ac.uns.ftn.informatika.rest.controller;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.domain.Organization;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.OrganizationDTO;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.RabbitCareOrganizationService;

import java.util.List;

@RestController
@RequestMapping(value = "api/organization")
public class RabbitCareOrganizationController {

    @Autowired
    private RabbitCareOrganizationService rabbitCareOrganizationService;

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrganizationDTO>> getAll() {
        List<OrganizationDTO> organizations = rabbitCareOrganizationService.findAll();
        return ResponseEntity.ok(organizations);
    }
}
