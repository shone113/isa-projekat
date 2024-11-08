package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class ProfileService {

    @Autowired
    private IProfileRepository profileRepository;

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
//        return followingProfiles.contains(userProfile.getId());
//        return userProfile.getFollowingProfiles().contains(creatorProfileId);
    }

}
