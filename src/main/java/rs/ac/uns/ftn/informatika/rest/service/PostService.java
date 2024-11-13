package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.GreetingDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @Override
    public Collection<Post> findAll() {
        Collection<Post> posts = postRepository.findAllPostsOrderByCreatedAtDesc();
        return posts;
    }

    @Override
    public Post findOne(Integer id) {
        Post post = postRepository.getOne(id);
        return post;
    }

    @Transactional
    public List<PostDTO> findPostsForUser(Integer userId) {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> filteredPostDTOs = new ArrayList<>();
        for(Post post : posts) {
            if(profileService.doesFollowPublisher(userId, post.getCreatorProfileId())){
                PostDTO postDTO = new PostDTO(post);
                Profile profile = profileService.getProfileByUserId(userId);
                postDTO.setLiked(postRepository.doesUserProfileLikedPost(profile.getId(), post.getId()));
                filteredPostDTOs.add(postDTO);
            }
        }

        return filteredPostDTOs;
    }

    @Override
    public Post create(PostDTO post) throws Exception {
        if (post.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Post savedPost = postRepository.save(new Post(post));
        return savedPost;
    }

    @Transactional
    public PostDTO likePost(int postId, int profileId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        postRepository.likePost(postId, profileId);

        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
        PostDTO postDTO = new PostDTO(post);
        postDTO.setLiked(true);
        return postDTO;
    }


    @Transactional
    public PostDTO unlikePost(int postId, int profileId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        postRepository.unlikePost(postId, profileId);

        post.setLikesCount(post.getLikesCount() - 1);
        postRepository.save(post);
        PostDTO postDTO = new PostDTO(post);
        postDTO.setLiked(false);
        return postDTO;
    }

    @Transactional
    public Post update(PostDTO post, Integer postId, Integer userId) throws Exception {
        Post postToUpdate = findOne(postId);
        if (postToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        Integer creatorProfileId = profileService.getProfileByUserId(userId).getId();
        if(postToUpdate.getCreatorProfileId() != creatorProfileId){
            throw new Exception("Unauthenticated user");
        }

        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setLikesCount(post.getLikesCount());
//        postToUpdate.setPublishingLocationId(post.getPublishingLocationId());
        postToUpdate.setPublishingDate(post.getPublishingDate());
        postToUpdate.setImage(post.getImage());
        postToUpdate.setComments(post.getComments());
        return postToUpdate;
    }

    public void delete(Integer postId, Integer creatorUserId) {
        Integer creatorProfileId = profileService.getProfileByUserId(creatorUserId).getId();
        Post post = postRepository.findById(postId).get();
        if(post.getCreatorProfileId() == creatorProfileId){
            postRepository.deleteById(postId);
        }
    }

//    @Override
//    public void deltePostById(Integer id){
//        postRepository.deletePostById(id);
//    }

}
