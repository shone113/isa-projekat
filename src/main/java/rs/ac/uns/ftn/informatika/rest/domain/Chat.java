package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = true)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_profile_id")
    private Profile adminProfile;

    @ManyToMany()
    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> members;

    public Chat() {}

    public Chat(String title, List<Profile> members, Profile adminProfile) {
        this.title = title;
        this.members = members;
        this.adminProfile = adminProfile;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Profile> getMembers() {
        return members;
    }

    public void setMembers(List<Profile> members) {
        this.members = members;
    }

    public Profile getAdminProfile() { return adminProfile; }

    public void setAdminProfile(Profile adminProfile) { this.adminProfile = adminProfile; }
}
