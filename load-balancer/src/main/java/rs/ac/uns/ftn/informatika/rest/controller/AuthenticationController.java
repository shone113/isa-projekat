package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.LoginDetailsDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserTokenState;
import rs.ac.uns.ftn.informatika.rest.exaption.ResourceConflictException;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;
import rs.ac.uns.ftn.informatika.rest.service.UserService;
import rs.ac.uns.ftn.informatika.rest.util.TokenUtils;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@RequestBody LoginDetailsDto authenticationRequest, HttpServletResponse response) {
        System.out.println("Ulazim u login endpoint");
        User userByEmail = userService.getByEmail(authenticationRequest.getEmail());
        if (userByEmail == null) {
            System.out.println("Email not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userByEmail.getActivationToken()!=null) {
            System.out.println("Activation token found");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        System.out.println("Autentifikuje se");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        boolean activated = this.userService.activateAccount(token);
        if (!activated)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token!");
        return ResponseEntity.ok("The account has been successfully activated!");
    }

}
