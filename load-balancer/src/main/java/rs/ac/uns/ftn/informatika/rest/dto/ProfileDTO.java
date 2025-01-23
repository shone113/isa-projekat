package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;

import java.util.List;
import java.util.Set;

public class ProfileDTO {
    private Integer id;
    private User user;
    private boolean chatMember;
    private Set<Profile> followingProfiles;
    private Set<Profile> followerProfiles;
    private Set<Post> posts;

    public ProfileDTO() {}

    public ProfileDTO(Profile profile) {
        this.id = profile.getId();
        this.user = profile.getUser();
        this.followerProfiles = profile.getFollowerProfiles();
        this.followingProfiles = profile.getFollowingProfiles();
        this.posts = profile.getPosts();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter and Setter for user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Getter and Setter for chatMember
    public boolean isChatMember() {
        return chatMember;
    }

    public void setChatMember(boolean chatMember) {
        this.chatMember = chatMember;
    }

    // Getter and Setter for followingProfiles
    public Set<Profile> getFollowingProfiles() {
        return followingProfiles;
    }

    public void setFollowingProfiles(Set<Profile> followingProfiles) {
        this.followingProfiles = followingProfiles;
    }

    // Getter and Setter for followerProfiles
    public Set<Profile> getFollowerProfiles() {
        return followerProfiles;
    }

    public void setFollowerProfiles(Set<Profile> followerProfiles) {
        this.followerProfiles = followerProfiles;
    }

    // Getter and Setter for posts
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
