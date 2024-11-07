package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.domain.Comment;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDate creationDate;
    private Integer creatorId;
    private Integer postId;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.creationDate = comment.getCreationDate();
        this.creatorId = comment.getCreatorId();
        this.postId = comment.getId();
    }

    public CommentDTO(Integer id, String content, LocalDate creationDate, Integer creatorId, Integer postId) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.creatorId = creatorId;
        this.postId = postId;
    }

    public CommentDTO() {}

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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public void setPost(Integer postId) {
        this.postId = postId;
    }
}
