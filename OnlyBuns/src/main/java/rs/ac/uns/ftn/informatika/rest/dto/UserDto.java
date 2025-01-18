package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.User;

public class UserDto {
    private Integer id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private int followersCount;

    private int followingCount;

    private int postsCount;

    private double latitude;

    private double longitude;
    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.followersCount = user.getFollowersCount();
        this.followingCount = user.getFollowingCount();
        this.postsCount = user.getPostsCount();
        this.latitude = user.getLatitude();
        this.longitude = user.getLongitude();
    }
    public UserDto(Integer id, String name, String surname, String email, String password, int followersCount, int followingCount, int postsCount) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.postsCount = postsCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFollowers() {return followersCount;}

    public void setFollowers(int followersCount) {this.followersCount = followersCount;}

    public int getFollowingCount() {return followingCount;}

    public void setFollowingCount(int followingCount) {this.followingCount = followingCount;}

    public int getPostsCount() {return postsCount;}

    public void setPostsCount(int postsCount) {this.postsCount = postsCount;}

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }
}
