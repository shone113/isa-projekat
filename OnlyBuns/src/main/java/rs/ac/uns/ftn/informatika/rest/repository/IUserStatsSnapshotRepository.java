package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.domain.UserStatsSnapshot;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IUserStatsSnapshotRepository extends JpaRepository<UserStatsSnapshot, Long> {
    Optional<UserStatsSnapshot> findFirstByUserOrderByTimestampDesc(User user);
    Optional<UserStatsSnapshot> findFirstByUserAndTimestampBeforeOrderByTimestampDesc(User user, LocalDateTime timestamp);
    Optional<UserStatsSnapshot> findByUserId(Integer userId);
}
