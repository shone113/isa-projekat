package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

@Service
public class UserCleanupService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ProfileService profileService;

    @Scheduled(cron = "0 0 0 L * ?") // Svakog poslednjeg dana u mesecu u ponoć
//@Scheduled(cron = "0 */5 * * * ?")  //na svakih 5 minuta
public void deleteInactiveAccounts() {
        for(User user : userRepository.findAll()) {
            if(user.getActivationToken() != null) {
                Profile profile = profileService.getProfileByUserId(user.getId());
                profileService.delete(profile);
                userRepository.delete(user);
            }
        }
    }
}
