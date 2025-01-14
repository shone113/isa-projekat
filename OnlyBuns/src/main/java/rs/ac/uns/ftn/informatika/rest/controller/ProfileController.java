package rs.ac.uns.ftn.informatika.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/all")
    public ResponseEntity<List<Profile>> getAll() {
        List<Profile> profiles = profileService.getAll();

        for (Profile profile : profiles) {
            System.out.println("Profile ID: " + profile.getId() +
                    ", Name: " + profile.getUser().getName() +
                    ", Surname: " + profile.getUser().getSurname());
        }

        return ResponseEntity.ok(profiles);
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
    @PutMapping("/follow/{followId}")
    public ResponseEntity<List<Profile>> followProfile(@PathVariable int followId, @RequestParam Integer id) {
        List<Profile> followers = profileService.followProfile(id, followId);
        return ResponseEntity.ok(followers);
    }

    @PutMapping("/unfollow/{unfollowId}")
    public ResponseEntity<List<Profile>> unfollowProfile(@PathVariable int unfollowId, @RequestParam Integer id) {
        List<Profile> followers = profileService.unfollowProfile(id, unfollowId);
        return ResponseEntity.ok(followers);
    }
    @GetMapping("/has-posts")
    public ResponseEntity<Map<String, Long>> getProfilesWithPostsCount() {
        Map<String, Long> profilesCount = profileService.getProfilesWithPostsCount();
        return ResponseEntity.ok(profilesCount);
    }
    @GetMapping("/has-comments-only")
    public ResponseEntity<Map<String, Long>> getProfilesWithCommentsOnlyCount() {
        Map<String, Long> profilesCount = profileService.getProfilesWithCommentsOnlyCount();
        return ResponseEntity.ok(profilesCount);
    }
    @GetMapping("/neither-posts-nor-comments")
    public ResponseEntity<Map<String, Long>> getProfilesWithoutPostsNorCommentsCount() {
        Map<String, Long> profilesCount = profileService.getProfilesWithoutPostsNorCommentsCount();
        return ResponseEntity.ok(profilesCount);
    }
}
