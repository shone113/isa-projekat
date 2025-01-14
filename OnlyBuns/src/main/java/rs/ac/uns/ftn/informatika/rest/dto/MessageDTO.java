package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MessageDTO {
    private Integer id;
    private String content;
    private LocalDateTime creationTime;
    private Integer creatorId;
    private String creatorName;
    private String creatorSurname;
    private Integer chatId;

    public MessageDTO() {}

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.creationTime = message.getCreationDate();
        this.creatorId = message.getCreator().getId();
        this.chatId = message.getChat().getId();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreationTime() { return creationTime; }
    public void setCreationDate(LocalDateTime creationTime) { this.creationTime = creationTime; }

    public Integer getCreatorId() { return creatorId; }
    public void setCreatorId(Integer creatorId) { this.creatorId = creatorId; }

    public String getCreatorName() { return creatorName; }
    public void setCreatorName(String creatorName) { this.creatorName = creatorName; }

    public String getCreatorSurname() { return creatorSurname; }
    public void setCreatorSurname(String creatorSurname) { this.creatorSurname = creatorSurname; }

    public Integer getChatId() { return chatId; }
    public void setChatId(Integer chatId) { this.chatId = chatId; }
}
