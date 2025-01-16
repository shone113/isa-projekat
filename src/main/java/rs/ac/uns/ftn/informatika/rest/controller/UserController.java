package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.LoginDetailsDto;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.UserService;
import org.springframework.data.domain.Pageable;

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

    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getUsersPaged(Pageable page) {

        Page<User> users = userService.findAll(page);
        Page<UserDto> userDtos = users.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDto> getById(@PathVariable int id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/user-by-id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserDto> findUserById(@PathVariable int id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(new UserDto(user));
    }

    @GetMapping("/registered")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getRegistratedUsers() {
        List<User> registratedUsers = userService.findRegistratedUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : registratedUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/filter/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getRegistratedUsersPaged(
//            @PathVariable String name,
            Pageable page,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer minPostsRange,
            @RequestParam(required = false) Integer maxPostsRange) {
        Page<User> filteredUsers = userService.filterUsersPaged(page, name, surname, email, minPostsRange, maxPostsRange);
        Page<UserDto> userDtos = filteredUsers.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/sort/following-count-asc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getSortedByFollowingCountAsc() {
        List<User> filteredUsers = userService.getSortedByFollowingCountAsc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/following-count-asc/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getSortedByFollowingCountAscPaged(Pageable page) {
        Page<User> sortedUsers = userService.getSortedByFollowingCountAscPaged(page);
        Page<UserDto> userDtos = sortedUsers.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/sort/following-count-desc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getSortedByFollowingCountDesc() {
        List<User> filteredUsers = userService.getSortedByFollowingCountDesc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/following-count-desc/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getSortedByFollowingCountDescPaged(Pageable page) {
        Page<User> sortedUsers = userService.getSortedByFollowingCountDescPaged(page);
        Page<UserDto> userDtos = sortedUsers.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/sort/email-asc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getSortedByEmailAsc() {
        List<User> filteredUsers = userService.getSortedByEmailAsc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/email-asc/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getSortedByEmailAscPaged(Pageable page) {
        Page<User> sortedUsers = userService.getSortedByEmailAscPaged(page);
        Page<UserDto> userDtos = sortedUsers.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/sort/email-desc")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getSortedByEmailDesc() {
        List<User> filteredUsers = userService.getSortedByEmailDesc();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : filteredUsers) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/sort/email-desc/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getSortedByEmailDesc(Pageable page) {
        Page<User> sortedUsers = userService.getSortedByEmailDescPaged(page);
        Page<UserDto> userDtos = sortedUsers.map(user -> new UserDto(user));
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/most-actived-users")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDto>> getMostActivedUsers(){
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user));
        }
        return ResponseEntity.ok(userDtos);
    }
}
