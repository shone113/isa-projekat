package rs.ac.uns.ftn.informatika.rest.domain;

import rs.ac.uns.ftn.informatika.rest.dto.UserDto;

import javax.persistence.*;

@Entity
@Table(name="userTab")
public class User{
    public enum Role {
        UNAUTHENTICATED_USER, AUTHENTICATED_USER, ADMINISTRATOR
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
//    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", unique = false, nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "followersCount")
    private int followers;

    @Column(name = "followingCount")
    private int followingCount;

    @Column(name = "postsCount")
    private int postsCount;

    public User() {
    }

    public User(UserDto userDto) {
        id = userDto.getId();
        name = userDto.getName();
        surname = userDto.getSurname();
        email = userDto.getEmail();
        password = userDto.getPassword();
        role = userDto.getRole();
    }
    public User(Integer id, String name, String surname, String email, String password, Role role) {
        super();
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getFollowers() {return followers;}

    public void setFollowers(int followers) {this.followers = followers;}

    public int getFollowingCount() {return followingCount;}

    public void setFollowingCount(int followingCount) {this.followingCount = followingCount;}

    public int getPostsCount() {return postsCount;}

    public void setPostsCount(int postsCount) {this.postsCount = postsCount;}

}
