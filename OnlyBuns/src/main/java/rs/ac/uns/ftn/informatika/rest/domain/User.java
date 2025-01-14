package rs.ac.uns.ftn.informatika.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Column(name = "followers_count", columnDefinition = "INTEGER")
    private Integer followersCount;

    @Column(name = "following_count", columnDefinition = "INTEGER")
    private Integer followingCount;

    @Column(name = "activation_token", nullable = true, length = 512)
    private String activationToken;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "posts_count")
    private Integer postsCount;

    @Column(name = "surname")
    private String surname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;

    public User() {
    }

    public User(UserDto userDto) {
        id = userDto.getId();
        name = userDto.getName();
        surname = userDto.getSurname();
        email = userDto.getEmail();
        password = userDto.getPassword();
        followersCount = userDto.getFollowers();
        followingCount = userDto.getFollowingCount();
        postsCount = userDto.getPostsCount();
        latitude = userDto.getLatitude();
        longitude = userDto.getLongitude();
    }
    public User(Integer id, String name, String surname, String email, String password, int followersCount, int followingCount, int postsCount) {
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

    public boolean isValid() {
        String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,4}$";
        return name != null && !name.isEmpty() && surname != null && !surname.isEmpty() && email != null && password != null && !password.isEmpty() && email.matches(emailRegex);
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

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFollowersCount() {return followersCount;}

    public void setFollowersCount(int followersCount) {this.followersCount = followersCount;}

    public int getFollowingCount() {return followingCount;}

    public void setFollowingCount(int followingCount) {this.followingCount = followingCount;}

    public int getPostsCount() {return postsCount;}

    public void setPostsCount(int postsCount) {this.postsCount = postsCount;}

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
