package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
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

}
