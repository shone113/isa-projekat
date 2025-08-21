package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_stats_snapshot")
public class UserStatsSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Long likesCount;
    private Long followersCount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public UserStatsSnapshot() {}

    public UserStatsSnapshot(User user, Long likesCount, Long followersCount, LocalDateTime timestamp) {
        this.user = user;
        this.likesCount = likesCount;
        this.followersCount = followersCount;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Long likesCount) {
        this.likesCount = likesCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Long followersCount) {
        this.followersCount = followersCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
