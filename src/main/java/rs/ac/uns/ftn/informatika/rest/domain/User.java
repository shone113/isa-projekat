package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="appUser")
public class User extends Person {
    @Column(name="postsCount", unique=false, nullable=true)
    private int postsCount;

    @Column(name="followingCount", unique=false, nullable=true)
    private int followingCount;

    @Column(name="authenticated", unique=false, nullable=true)
    private boolean authenticated;

    public User() {}

    public User(int postsCount, int followingCount, boolean authenticated) {
        super();
        this.postsCount = postsCount;
        this.followingCount = followingCount;
        this.authenticated = authenticated;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}
