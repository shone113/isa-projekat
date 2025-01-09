package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;

@Entity
@Table(name = "chat_members")
public class ChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "joining_message", nullable = true)
    private Integer joiningMessage;

    public ChatMember() {}

    public ChatMember(Chat chat, Profile profile) {
        this.id = id;
        this.chat = chat;
        this.profile = profile;
    }
    public ChatMember(Chat chat, Profile profile, Integer joiningMessage) {
        this.id = id;
        this.chat = chat;
        this.profile = profile;
        this.joiningMessage = joiningMessage;
    }
    public Integer getJoiningMessage(){ return joiningMessage; }
    public void setJoiningMessage(Integer joiningMessage){ this.joiningMessage = joiningMessage; }

    public Profile getProfile() { return profile; }
}
