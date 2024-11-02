package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.List;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int likesCount;
    private int publishingLocationId;
    private LocalDate publishingDate;
    private String image;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {}

    public Post(PostDTO postDTO) {
        this.id = postDTO.getId();
        this.description = postDTO.getDescription();
        this.likesCount = postDTO.getLikesCount();
        this.publishingLocationId = postDTO.getPublishingLocationId();
        this.publishingDate = postDTO.getPublishingDate();
        this.image = postDTO.getImage();
        this.comments = postDTO.getComments();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getPublishingLocationId() {
        return publishingLocationId;
    }

    public void setPublishingLocationId(int publishingLocationId) {
        this.publishingLocationId = publishingLocationId;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setPublishingDate(LocalDate publishingDate) {
        this.publishingDate = publishingDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void incrementLikesCount() {
        this.likesCount++;
    }

    public void decrementLikesCount() {
        this.likesCount--;
    }
}
