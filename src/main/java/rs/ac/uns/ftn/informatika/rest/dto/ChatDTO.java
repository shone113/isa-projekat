package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Chat;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;

import java.util.List;

public class ChatDTO {
    private Integer id;
    private String title;
    private List<Profile> members;
    private Integer adminProfileId;
    private Chat.ChatType chatType;

    public ChatDTO(){}

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.title = chat.getTitle();
        this.members = chat.getMembers();
        this.adminProfileId = chat.getAdminProfile().getId();
        this.chatType = chat.getChatType();
    }
    public ChatDTO(Integer id, String title, List<Profile> members, Integer adminProfileId, Chat.ChatType chatType) {
        this.id = id;
        this.title = title;
        this.members = members;
        this.adminProfileId = adminProfileId;
        this.chatType = chatType;
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

    public Integer getAdminProfileId() { return adminProfileId; }
    public void setAdminProfileId(Integer adminProfileId) { this.adminProfileId = adminProfileId; }

    public Chat.ChatType getChatType() { return chatType;}
    public void setChatType(Chat.ChatType chatType) { this.chatType = chatType; }
}
