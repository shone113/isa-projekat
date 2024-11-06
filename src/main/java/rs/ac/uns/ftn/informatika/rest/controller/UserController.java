package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = new User(userDto);
        user = userService.register(user);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/registered")
    public ResponseEntity<List<UserDto>> getRegistratedUsers() {
        List<User> registratedUsers = userService.findRegistratedUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : registratedUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/filter/{name}")
    public ResponseEntity<List<UserDto>> getRegistratedUsers(@PathVariable String name) {
        List<User> filteredUsers = userService.filterUsers(name);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("activate/{email}/{code}")
    public ResponseEntity<UserDto> activateAccount(@PathVariable String code, @PathVariable String email) {
        User user = userService.activateAccount(code, email);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new UserDto(user));
    }
}
