package rs.ac.uns.ftn.informatika.rest.dto;

public class ImageDTO {
    private Integer postId;
    private String image;
    private Integer creatorProfileId;

    public ImageDTO(Integer postId, String image, Integer creatorProfileId) {
        this.postId = postId;
        this.image = image;
        this.creatorProfileId = creatorProfileId;
    }
    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Integer getCreatorProfileId() { return creatorProfileId; }
    public void setCreatorProfileId(Integer creatorProfileId) { this.creatorProfileId = creatorProfileId; }
}
