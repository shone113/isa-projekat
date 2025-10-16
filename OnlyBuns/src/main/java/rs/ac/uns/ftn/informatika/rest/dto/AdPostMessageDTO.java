package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Post;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AdPostMessageDTO implements Serializable {

    private String description;
    private LocalDateTime publishingDate;
    private String username;

    public AdPostMessageDTO() {}

    public AdPostMessageDTO(PostDTO post) {
        this.description = post.getDescription();
        this.publishingDate = post.getPublishingDate();
        this.username = post.getCreatorUsername();
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getPublishingDate() { return publishingDate; }
    public void setPublishingDate(LocalDateTime publishingDate) { this.publishingDate = publishingDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Override
    public String toString() {
        return "AdPostMessageDTO{" +
                "description='" + description + '\'' +
                ", publishedAt=" + publishingDate +
                ", username='" + username + '\'' +
                '}';
    }
}