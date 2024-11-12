package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.LoginDetailsDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserTokenState;
import rs.ac.uns.ftn.informatika.rest.exaption.ResourceConflictException;
import rs.ac.uns.ftn.informatika.rest.service.UserService;
import rs.ac.uns.ftn.informatika.rest.util.TokenUtils;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody LoginDetailsDto authenticationRequest, HttpServletResponse response) {

        User userByEmail = userService.getByEmail(authenticationRequest.getEmail());
        if (userByEmail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (userByEmail.getActivationToken()!=null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<User> addUser(@RequestBody UserDto userDto, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.getByEmail(userDto.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(existUser.getId(), "Username already exists");
        }

        User user = this.userService.register(new User(userDto));
        String jwt = tokenUtils.generateToken(user.getUsername());
        userService.setActivationToken(user, jwt);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateAccount(@RequestParam("token") String token) {
        boolean activated = this.userService.activateAccount(token);
        if (!activated)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid token!");
        return ResponseEntity.ok("The account has been successfully activated!");
    }

}
