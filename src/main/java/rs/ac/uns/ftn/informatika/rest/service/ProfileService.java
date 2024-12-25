package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;

    @Transactional
    public Profile create(User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = new Profile();
        profile.setUser(managedUser);
        return profileRepository.save(profile);
    }

    @Transactional
    public boolean doesFollowPublisher(Integer userId, Integer creatorProfileId) {
        Profile userProfile = profileRepository.findProfilesByUserId(userId);
        if (userProfile == null || creatorProfileId == null) {
            throw new IllegalArgumentException("User profile or creator profile is null");
        }
        List<Profile> followingProfiles = profileRepository.findFollowingProfiles(userProfile.getId());

        boolean isFollowing = followingProfiles.stream()
                .anyMatch(profile -> profile.getId() == creatorProfileId);

        return isFollowing;
    }

    @Transactional
    public Profile getProfileByUserId(Integer userId) {
        return profileRepository.findProfilesByUserId(userId);
    }

    @Transactional
    public List<Profile> getAll(){
//        return profileRepository.findAll();
        try {
            List<Profile> profiles = profileRepository.findAll();
            //System.out.println("Fetched profiles count: " + profiles.size());
            for(Profile profile : profiles) {
                profile.setPosts(null);
            }
            return profiles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching profiles: " + e.getMessage());
        }
    }

    @Transactional
    public Profile getProfileById(Integer profileId) {
        Profile profile = profileRepository.findProfileById(profileId);
        return profile;
    }

    @Transactional
    public List<Profile> getFollowingProfiles(Integer id) {
        List<Profile> followingProfiles = profileRepository.findFollowingProfiles(id);
        for(Profile profile : followingProfiles) {
            profile.setPosts(null);
        }
        System.out.println("Followers count: " + followingProfiles.size());
        return followingProfiles;
    }

    @Transactional
    public List<Profile> getFollowerProfiles(Integer id) {
        List<Profile> followerProfiles = profileRepository.findFollowerProfiles(id);
        for(Profile profile : followerProfiles) {
            profile.setPosts(null);
        }
        System.out.println("Followers count: " + followerProfiles.size());
        return followerProfiles;
    }

    @Transactional
    public List<Profile> followProfile(Integer id, Integer followId){
        List<Profile> followingProfiles = profileRepository.findFollowingProfiles(id);
        System.out.println("Fetched profiles count: " + followingProfiles.size());

        boolean alreadyFollowing = followingProfiles.stream()
                .anyMatch(profile -> profile.getId().equals(followId));

        Profile currentProfile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Current profile not found"));

        if (!alreadyFollowing) {
            Profile profileToFollow = profileRepository.findById(followId).orElseThrow(() -> new RuntimeException("Profile to follow not found"));
            followingProfiles.add(profileToFollow);

            currentProfile.getFollowingProfiles().add(profileToFollow);

            profileRepository.save(currentProfile);

            profileToFollow.getFollowerProfiles().add(currentProfile);
            profileRepository.save(profileToFollow);

            //increment following for user
            Integer currentUserId = currentProfile.getUser().getId();
            User currentUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            int currentFollowingCount = currentUser.getFollowingCount();
            currentUser.setFollowingCount(currentFollowingCount + 1);
            userRepository.save(currentUser);

            //increment followers for followed user
            Integer followingUserId = profileToFollow.getUser().getId();
            User followingUser = userRepository.findById(followingUserId)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            int followersCount = followingUser.getFollowersCount();
            followingUser.setFollowersCount(followersCount + 1);
            userRepository.save(followingUser);

            //profileRepository.saveAll(followingProfiles); // Samo ako je potrebno sačuvati promene
        }
        System.out.println("NEW Fetched profiles count: " + followingProfiles.size());

        List<Profile> followerProfiles = profileRepository.findFollowerProfiles(followId);
        for(Profile profile : followerProfiles) {
            profile.setPosts(null);
        }
        System.out.println("Following count: " + followerProfiles.size());
        currentProfile.setPosts(null);
        return followerProfiles;
    }

    @Transactional
    public List<Profile> unfollowProfile(Integer id, Integer unfollowId) {
        List<Profile> followingProfiles = profileRepository.findFollowingProfiles(id);
        System.out.println("Fetched profiles count: " + followingProfiles.size());

        boolean following = followingProfiles.stream()
                .anyMatch(profile -> profile.getId().equals(unfollowId));

        Profile currentProfile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Current profile not found"));

        if (following) {
            Profile profileToUnfollow = profileRepository.findById(unfollowId).orElseThrow(() -> new RuntimeException("Profile to follow not found"));
            followingProfiles.remove(profileToUnfollow);

            currentProfile.getFollowingProfiles().remove(profileToUnfollow);

            profileRepository.save(currentProfile);

            profileToUnfollow.getFollowerProfiles().remove(currentProfile);
            profileRepository.save(profileToUnfollow);

            //increment following for user
            Integer currentUserId = currentProfile.getUser().getId();
            User currentUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            int currentFollowingCount = currentUser.getFollowingCount();
            currentUser.setFollowingCount(currentFollowingCount - 1);
            userRepository.save(currentUser);

            //increment followers for followed user
            Integer followingUserId = profileToUnfollow.getUser().getId();
            User followingUser = userRepository.findById(followingUserId)
                    .orElseThrow(() -> new RuntimeException("Current user not found"));

            int followersCount = followingUser.getFollowersCount();
            followingUser.setFollowersCount(followersCount - 1);
            userRepository.save(followingUser);

            //profileRepository.saveAll(followingProfiles); // Samo ako je potrebno sačuvati promene
        }
        System.out.println("NEW Fetched profiles count: " + followingProfiles.size());

//        for(Profile profile : followingProfiles) {
//            profile.setPosts(null);
//        }
        System.out.println("Following count: " + followingProfiles.size());
        currentProfile.setPosts(null);

        List<Profile> followerProfiles = profileRepository.findFollowerProfiles(unfollowId);
        for(Profile profile : followerProfiles) {
            profile.setPosts(null);
        }
        return followerProfiles;
    }
}
