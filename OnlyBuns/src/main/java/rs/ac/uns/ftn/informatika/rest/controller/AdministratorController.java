package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.domain.Administrator;
import rs.ac.uns.ftn.informatika.rest.service.AdministratorService;
import rs.ac.uns.ftn.informatika.rest.service.IAdministratorService;

import java.util.Collection;

@Tag(name = "Greeting controller", description = "The greeting API")
@RestController
@RequestMapping("/api/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Operation(description = "Get all greetings", method="GET")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Administrator>> getAdministrators() {
        Collection<Administrator> administrators = administratorService.findAll();
        return new ResponseEntity<Collection<Administrator>>(administrators, HttpStatus.OK);
    }
}
