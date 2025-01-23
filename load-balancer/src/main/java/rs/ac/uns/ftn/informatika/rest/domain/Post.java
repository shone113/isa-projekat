package rs.ac.uns.ftn.informatika.rest.domain;

import javax.persistence.*;
import javax.xml.crypto.Data;
import javax.xml.stream.Location;
import java.time.LocalDate;
import java.util.List;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "likes_count", nullable = false)
    private int likesCount;

    @Column(name = "publishing_location_id")
    private Long publishingLocationId;

    @Column(name = "publishing_date", nullable = false)
    private LocalDate publishingDate;

    @Column(name = "image", nullable = true)
    private String image;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profile;


    @ManyToMany()
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> likedBy;

    @Column(name = "longitude")
    private double longitude;
    @Column(name = "latitude")
    private double latitude;

    public Post() {}

    public Post(PostDTO postDTO) {
        this.id = postDTO.getId();
        this.description = postDTO.getDescription();
        this.likesCount = postDTO.getLikesCount();
//        this.publishingLocationId = postDTO.getPublishingLocationId();
        this.publishingDate = postDTO.getPublishingDate();
        this.image = postDTO.getImage();
        this.comments = postDTO.getComments();
        this.latitude = postDTO.getLatitude();
        this.longitude = postDTO.getLongitude();
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

    //public Integer getCreatorProfileId() { return this.profile != null && this.profile.getUser() != null ? this.profile.getUser().getId() : null;}
    public Integer getCreatorProfileId() { return this.profile.getId(); }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
