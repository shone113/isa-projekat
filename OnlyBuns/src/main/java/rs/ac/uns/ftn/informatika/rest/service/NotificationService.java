package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.UserStatsSnapshot;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserStatsSnapshotRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository; // Ako ga imate
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired private IUserRepository userRepository;
    @Autowired private IUserStatsSnapshotRepository userStatsSnapshotRepository;
    @Autowired private MailService emailService;
    @Autowired private IUserStatsSnapshotRepository statsRepository;
    @Autowired private IProfileRepository profileRepository;
    @Autowired private IPostRepository postRepository;

    @Transactional
    @Scheduled(cron = "0 */5 * * * ?")
    public void runDailyReportAndNotifications() throws InterruptedException {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserStatsSnapshot snapshot = userStatsSnapshotRepository.findByUserId(user.getId())
                    .orElse(new UserStatsSnapshot());
            snapshot.setUser(user);

            Profile userProfile = profileRepository.findProfilesByUserId(user.getId());
            if (userProfile != null) {
                Long totalLikes = postRepository.sumLikesCountByProfile(userProfile);
                snapshot.setLikesCount(totalLikes != null ? totalLikes : 0L);

                snapshot.setFollowersCount((long) userProfile.getFollowerProfiles().size());
            } else {
                snapshot.setLikesCount(0L);
                snapshot.setFollowersCount(0L);
            }

            snapshot.setTimestamp(LocalDateTime.now());
            statsRepository.save(snapshot);
        }

        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<User> inactiveUsers = userRepository.findByLastLoginDateBefore(sevenDaysAgo);

        for (User user : inactiveUsers) {
            String statsSummary = getUserStatisticsFromSnapshots(user, sevenDaysAgo);

            emailService.SendInactivityEmail(
                    user.getEmail(),
                    "Vaš nedeljni pregled aktivnosti",
                    statsSummary
            );
        }
    }

    private String getUserStatisticsFromSnapshots(User user, LocalDateTime fromDate) {
        Optional<UserStatsSnapshot> currentSnapshot = statsRepository.findFirstByUserOrderByTimestampDesc(user);
        Optional<UserStatsSnapshot> oldSnapshot = statsRepository.findFirstByUserAndTimestampBeforeOrderByTimestampDesc(user, fromDate);

        long newLikes = 0;
        long newFollowers = 0;

        if (currentSnapshot.isPresent() && oldSnapshot.isPresent()) {
            newLikes = currentSnapshot.get().getLikesCount() - oldSnapshot.get().getLikesCount();
            newFollowers = currentSnapshot.get().getFollowersCount() - oldSnapshot.get().getFollowersCount();
        }

        return "Zdravo " + user.getName() + ",\n\n"
                + "Evo tvog pregleda aktivnosti iz prethodnih 7 dana:\n"
                + "- Broj novih lajkova: " + newLikes + "\n"
                + "- Broj novih pratilaca: " + newFollowers + "\n"
                + "\nPoseti nas ponovo da vidiš šta se novo dešava!";
    }
}
