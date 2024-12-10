package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    @GetMapping()
    public ResponseEntity<Profile> getById(@RequestParam Integer id) {
        Profile profile = profileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/following")
    public ResponseEntity<List<Profile>> getFollowingProfile(@RequestParam Integer id) {
        List<Profile> followingProfiles = profileService.getFollowingProfiles(id);
        return ResponseEntity.ok(followingProfiles);
    }

    @GetMapping("/follower")
    public ResponseEntity<List<Profile>> getFollowerProfile(@RequestParam Integer id) {
        List<Profile> followerProfiles = profileService.getFollowerProfiles(id);
        return ResponseEntity.ok(followerProfiles);
    }
    @GetMapping("/user")
    public ResponseEntity<Profile> getByUserId(@RequestParam Integer id) {
        Profile profile = profileService.getProfileByUserId(id);
        return ResponseEntity.ok(profile);
    }
}
