package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id")
    private Profile creator;

    @Column(name = "creator_name", nullable = false)
    private String creatorName;

    @Column(name = "creator_surname", nullable = false)
    private String creatorSurname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public Message() {}

    public Message(String content, LocalDate creationDate, Profile creator, String creatorName, String creatorSurname) {
        this.content = content;
        this.creationDate = creationDate;
        this.creator = creator;
        this.creatorName = creatorName;
        this.creatorSurname = creatorSurname;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Profile getCreator() {
        return creator;
    }

    public void setCreator(Profile creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorSurname() {
        return creatorSurname;
    }

    public void setCreatorSurname(String creatorSurname) {
        this.creatorSurname = creatorSurname;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}

