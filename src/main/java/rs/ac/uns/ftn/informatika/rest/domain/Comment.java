package rs.ac.uns.ftn.informatika.rest.domain;

import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class Comment {
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
    //private User creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(){}

    public Comment(String content, LocalDate creationDate, Post post) {
        this.content = content;
        this.creationDate = creationDate;
        this.post = post;
    }

    public Comment(CommentDTO commentDTO) {
        this.content = commentDTO.getContent();
        this.creationDate = commentDTO.getCreationDate();
//        this.creator = commentDTO.getCreator();
//        this.creator.setId(commentDTO.getCreatorId());
//        this.post = commentDTO.getPost();
//        this.post.setId(commentDTO.getPostId());
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

//    public Long getCreatorId() {
//        return creatorId;
//    }
//
//    public void setCreatorId(Long creatorId) {
//        this.creatorId = creatorId;
//    }
    public Integer getPostId(){ return this.post.getId();}

    public Integer getCreatorId(){ return this.creator.getId();}

    //public void setCreator(User creator) {this.creator = creator;}
    public void setCreator(Profile creator) {this.creator = creator;}

    public void setPost(Post post) {this.post = post;}
}
