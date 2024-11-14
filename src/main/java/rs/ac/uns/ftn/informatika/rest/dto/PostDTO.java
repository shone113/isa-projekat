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
    private Integer creatorProfileId;
    private boolean liked;
    private String creatorName;
    private String creatorSurname;

    public PostDTO() {}

    public PostDTO(Integer id, String description, int likesCount, int creatorId, String creatorName, String creatorSurname){
        super();
        this.id = id;
        this.description = description;
        this.likesCount = likesCount;
//        this.publishingLocationId = publishingLocationId;
        this.publishingDate = LocalDate.now();
        this.image = "";
        this.comments = new ArrayList<>();
        this.creatorProfileId = creatorId;
        this.creatorName = creatorName;
        this.creatorSurname = creatorSurname;
    }
    public PostDTO(Post post) {
        this.id = post.getId();
        this.description = post.getDescription();
        this.likesCount = post.getLikesCount();
//        this.publishingLocationId = post.getPublishingLocationId();
        this.publishingDate = post.getPublishingDate();
        this.image = post.getImage();
        this.comments = post.getComments();
        this.creatorProfileId = post.getCreatorProfileId() != null ? post.getCreatorProfileId() : null;
        this.creatorName = "";
        this.creatorSurname = "";
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
    public void setLiked(boolean liked) { this.liked = liked; }
    public boolean getLiked() { return liked; }
    public Integer getCreatorProfileId(){ return this.creatorProfileId; }
    public void setCreatorName(String creatorName){ this.creatorName = creatorName; }
    public void setCreatorSurname(String creatorSurname){ this.creatorSurname = creatorSurname; }
    public String getCreatorName(){ return this.creatorName; }
    public String getCreatorSurname(){ return this.creatorSurname; }
}
