package rs.ac.uns.ftn.informatika.dto;

import java.time.LocalDateTime;

public class AdPostMessageDTO {
    private String description;
    private String username;
    private LocalDateTime publishingDate;

    public AdPostMessageDTO() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDateTime getPublishingDate() { return publishingDate; }
    public void setPublishingDate(LocalDateTime publishingDate) { this.publishingDate = publishingDate; }
}
