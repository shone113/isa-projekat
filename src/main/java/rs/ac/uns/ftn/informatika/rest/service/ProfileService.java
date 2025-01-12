package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.util.LinkedCaseInsensitiveMap;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.ICommentRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import org.springframework.transaction.annotation.Propagation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ICommentRepository commentRepository;

    @Transactional
    public Profile create(User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = new Profile();
        profile.setUser(managedUser);
        return profileRepository.save(profile);
    }
    @Transactional
    public void delete(Profile profile) {
        profileRepository.delete(profile);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Profile> followProfile(Integer id, Integer followId){
        User currentUser = userRepository.findUserByID(id);
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
//            User currentUser = userRepository.findById(currentUserId)
//                    .orElseThrow(() -> new RuntimeException("Current user not found"));

//            int currentFollowingCount = currentUser.getFollowingCount();
//            currentUser.setFollowingCount(currentFollowingCount + 1);
//            userRepository.save(currentUser);
            userRepository.incrementFollowingCount(currentUserId);

            //increment followers for followed user
            Integer followingUserId = profileToFollow.getUser().getId();
//            User followingUser = userRepository.findById(followingUserId)
//                    .orElseThrow(() -> new RuntimeException("Current user not found"));
//
//            int followersCount = followingUser.getFollowersCount();
//            followingUser.setFollowersCount(followersCount + 1);
//            userRepository.save(followingUser);
            userRepository.incrementFollowersCount(followingUserId);
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
        User currentUser = userRepository.findUserByID(id);
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
//            User currentUser = userRepository.findById(currentUserId)
//                    .orElseThrow(() -> new RuntimeException("Current user not found"));
//
//            int currentFollowingCount = currentUser.getFollowingCount();
//            currentUser.setFollowingCount(currentFollowingCount - 1);
//            userRepository.save(currentUser);
            userRepository.decrementFollowingCount(currentUserId);

            //increment followers for followed user
            Integer followingUserId = profileToUnfollow.getUser().getId();
//            User followingUser = userRepository.findById(followingUserId)
//                    .orElseThrow(() -> new RuntimeException("Current user not found"));
//
//            int followersCount = followingUser.getFollowersCount();
//            followingUser.setFollowersCount(followersCount - 1);
//            userRepository.save(followingUser);
            userRepository.decrementFollowersCount(followingUserId);

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

    @Transactional
    public Map<String, Long> getProfilesWithPostsCount(){
        List<Profile> profiles = profileRepository.findAll();
        List<Post> posts = postRepository.findAll();

        long profilesWithPost = profiles.stream()
                .filter(profile -> posts.stream()
                        .anyMatch(post -> post.getCreatorProfileId().equals(profile.getId())))
                .count();

        double profilesWithPostsPercent = ((double)profilesWithPost / profiles.size()) * 100;

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("Users who have made a post", (long)profilesWithPostsPercent);
        result.put("Users who have not made a post", (long)(100 - profilesWithPostsPercent));

        return result;
    }

    @Transactional
    public Map<String, Long> getProfilesWithCommentsOnlyCount(){
        List<Profile> profiles = profileRepository.findAll();
        List<Comment> comments = commentRepository.findAll();
        List<Post> posts = postRepository.findAll();


        // Profili koji su napravili komentare
        Set<Integer> profilesWithComments = profiles.stream()
                .filter(profile -> comments.stream()
                        .anyMatch(comment -> comment.getCreatorId().equals(profile.getId())))
                .map(Profile::getId)
                .collect(Collectors.toSet());

        // Profili koji su napravili postove
        Set<Integer> profilesWithPosts = profiles.stream()
                .filter(profile -> posts.stream()
                        .anyMatch(post -> post.getCreatorProfileId().equals(profile.getId())))
                .map(Profile::getId)
                .collect(Collectors.toSet());

        // Profili sa komentarima, ali bez postova
        long profilesWithCommentsButNoPosts = profilesWithComments.stream()
                .filter(profileId -> !profilesWithPosts.contains(profileId))
                .count();

        double profilesWithCommentsOnlyPercent = ((double)profilesWithCommentsButNoPosts / profiles.size()) * 100;

        System.out.println("idjevi profila sa komentarima: " + profilesWithComments);
        System.out.println("Idijevi profila sa postovima: " + profilesWithPosts);
        System.out.println("Idijevi profila komentara bez postovima: " + profilesWithCommentsButNoPosts);
        System.out.println("Broj profila sa komentarima: " + profilesWithComments.size());
        System.out.println("Broj profila sa postovima: " + profilesWithPosts.size());

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("Users who have made comments only", (long)profilesWithCommentsOnlyPercent);
        result.put("Other users", (long)(100 - profilesWithCommentsOnlyPercent));

        return result;
    }

    @Transactional
    public Map<String, Long> getProfilesWithoutPostsNorCommentsCount(){
        List<Profile> profiles = profileRepository.findAll();
        List<Post> posts = postRepository.findAll();
        List<Comment> comments = commentRepository.findAll();

        // Extract profile IDs that have made comments
        Set<Integer> profilesWithComments = comments.stream()
                .map(Comment::getCreatorId)
                .collect(Collectors.toSet());

        // Extract profile IDs that have made posts
        Set<Integer> profilesWithPosts = posts.stream()
                .map(Post::getCreatorProfileId)
                .collect(Collectors.toSet());

        // Combine the sets of profiles with posts and comments
        Set<Integer> profilesWithPostsOrComments = new HashSet<>(profilesWithComments);
        profilesWithPostsOrComments.addAll(profilesWithPosts);

        // Identify profiles that have neither posts nor comments
        List<Profile> profilesWithoutPostsOrComments = profiles.stream()
                .filter(profile -> !profilesWithPostsOrComments.contains(profile.getId()))
                .collect(Collectors.toList());

        double profilesPercents = ((double)profilesWithoutPostsOrComments.size() / profiles.size()) * 100;


        Map<String, Long> result = new LinkedHashMap<>();
        result.put("Users who have made neither post nor comments", (long)profilesPercents);
        result.put("Users who have made posts or comments", (long)(100 - profilesPercents));

        return result;
    }
}
