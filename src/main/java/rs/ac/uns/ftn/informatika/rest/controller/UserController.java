package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.LoginDetailsDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@RequestParam int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user));
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

    @GetMapping("/filter")
    public ResponseEntity<List<UserDto>> getRegistratedUsers(
//            @PathVariable String name,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer minPostsRange,
            @RequestParam(required = false) Integer maxPostsRange) {
        List<User> filteredUsers = userService.filterUsers(name, surname, email, minPostsRange, maxPostsRange);
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/following-count-asc")
    public ResponseEntity<List<UserDto>> getSortedByFollowingCountAsc() {
        List<User> filteredUsers = userService.getSortedByFollowingCountAsc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/following-count-desc")
    public ResponseEntity<List<UserDto>> getSortedByFollowingCountDesc() {
        List<User> filteredUsers = userService.getSortedByFollowingCountDesc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/email-asc")
    public ResponseEntity<List<UserDto>> getSortedByEmailAsc() {
        List<User> filteredUsers = userService.getSortedByEmailAsc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/email-desc")
    public ResponseEntity<List<UserDto>> getSortedByEmailDesc() {
        List<User> filteredUsers = userService.getSortedByEmailDesc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

//    @GetMapping("activate/{email}/{code}")
//    public ResponseEntity<UserDto> activateAccount(@PathVariable String code, @PathVariable String email) {
//        User user = userService.activateAccount(code, email);
//        if (user == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(new UserDto(user));
//    }

//
//    @GetMapping("/code/{email}")
//    public ResponseEntity<String> sendActivationCode(@PathVariable String email) {
//        userService.sendActivateCode(email);
//        return ResponseEntity.ok("");
//    }
}
