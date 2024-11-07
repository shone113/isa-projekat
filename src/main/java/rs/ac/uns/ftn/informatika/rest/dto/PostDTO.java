package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {
    private Integer id;
    private String description;
    private int likesCount;
//    private int publishingLocationId;
    private LocalDate publishingDate;
    private String image;
    private List<Comment> comments;

    public PostDTO() {}

    public PostDTO(Integer id, String description, int likesCount){
        super();
        this.id = id;
        this.description = description;
        this.likesCount = likesCount;
//        this.publishingLocationId = publishingLocationId;
        this.publishingDate = LocalDate.now();
        this.image = "";
        this.comments = new ArrayList<>();
    }
    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.likesCount = post.getLikesCount();
//        this.publishingLocationId = post.getPublishingLocationId();
        this.publishingDate = post.getPublishingDate();
        this.image = post.getImage();
        this.comments = post.getComments();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

//    public int getPublishingLocationId() {
//        return publishingLocationId;
//    }
//
//    public void setPublishingLocationId(int publishingLocationId) {
//        this.publishingLocationId = publishingLocationId;
//    }

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
}
