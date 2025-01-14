package rs.ac.uns.ftn.informatika.rest.domain;

import rs.ac.uns.ftn.informatika.rest.dto.ChatDTO;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "chats")
public class Chat {
    public enum ChatType {
        GROUP,
        DM
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)  // This will store the enum as a string in the database
    @Column(name = "chat_type")
    private ChatType chatType;


    @Column(name = "title", nullable = true)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_profile_id")
    private Profile adminProfile;

//    @ManyToMany()
//    @JoinTable(
//            name = "chat_members",
//            joinColumns = @JoinColumn(name = "chat_id"),
//            inverseJoinColumns = @JoinColumn(name = "profile_id")
//    )
//    private List<Profile> members;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMember> members;


    public Chat() {}

    public Chat(String title, List<Profile> members, Profile adminProfile, ChatType chatType) {
        this.title = title;
//        this.members = members;
        this.adminProfile = adminProfile;
        this.chatType = chatType;
        this.members = new ArrayList<>();

        for (Profile profile : members) {
            this.members.add(new ChatMember(this, profile));
        }
    }
    public Chat(ChatDTO chatDTO) {
        this.id = chatDTO.getId();
        this.title = chatDTO.getTitle();
//        this.members = chatDTO.getMembers();
        this.chatType = chatDTO.getChatType();
        this.members = new ArrayList<>();
        for (Profile profile : chatDTO.getMembers()) {
            this.members.add(new ChatMember(this, profile));
        }
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

//    public List<Profile> getMembers() {
//        return members;
//    }
//
//    public void setMembers(List<Profile> members) { this.members = members; }

    public List<Profile> getMemberProfiles() {
        List<Profile> profiles = new ArrayList<>();
        for (ChatMember member : members) {
            profiles.add(member.getProfile()); // Uzmi Profile iz ChatMember
        }
        return profiles;
    }

    public List<ChatMember> getMembers() {
        return members;
    }

    public void setMembers(List<ChatMember> members) { this.members = members; }

    public Profile getAdminProfile() { return adminProfile; }

    public void setAdminProfile(Profile adminProfile) { this.adminProfile = adminProfile; }

    public ChatType getChatType() { return chatType;}
    public void setChatType(ChatType chatType) { this.chatType = chatType; }
}
