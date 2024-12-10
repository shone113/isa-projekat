package rs.ac.uns.ftn.informatika.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "profile_following",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "following_profile_id")
    )
    @JsonIgnore
    private Set<Profile> followingProfiles;

    @ManyToMany
    @JoinTable(
            name = "profile_follower",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_profile_id")
    )
    @JsonIgnore
    private Set<Profile> followerProfiles;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Post> posts;

//    @ManyToMany(mappedBy = "likedBy")
//    private List<Post> likedPosts;

    public Profile() {
    }

    public Profile(Integer id, User user) {
        super();
        this.id = id;
        this.user = user;
    }

    public Integer getId() { return id; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Profile> getFollowingProfiles() {
        return followingProfiles;
    }

    public void setFollowingProfiles(Set<Profile> followingProfiles) {
        this.followingProfiles = followingProfiles;
    }

    public Set<Profile> getFollowerProfiles() {
        return followerProfiles;
    }

    public void setFollowerProfiles(Set<Profile> followerProfiles) {
        this.followerProfiles = followerProfiles;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
