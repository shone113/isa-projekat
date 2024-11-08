package rs.ac.uns.ftn.informatika.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.uns.ftn.informatika.rest.dto.UserDto;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="userTab")
public class User implements UserDetails {
    public enum Role_enum {
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

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "followersCount")
    private int followersCount;

    @Column(name = "followingCount")
    private int followingCount;

    @Column(name = "postsCount")
    private int postsCount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(name = "isActivated")
    private boolean isActivated = false;
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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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
}
