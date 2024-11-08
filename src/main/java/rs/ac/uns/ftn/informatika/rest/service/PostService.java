package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.GreetingDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

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
        Collection<Post> posts = postRepository.findAll();
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
                filteredPostDTOs.add(new PostDTO(post));
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
    public PostDTO likePost(int postId){
        Post post = postRepository.getOne(postId);
        post.incrementLikesCount();
        return new PostDTO(post);
    }
    @Override
    @Transactional
    public Post update(PostDTO post, Integer id) throws Exception {
        Post postToUpdate = findOne(id);
        if (postToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setLikesCount(post.getLikesCount());
//        postToUpdate.setPublishingLocationId(post.getPublishingLocationId());
        postToUpdate.setPublishingDate(post.getPublishingDate());
        postToUpdate.setImage(post.getImage());
        postToUpdate.setComments(post.getComments());
        return postToUpdate;
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

//    @Override
//    public void deltePostById(Integer id){
//        postRepository.deletePostById(id);
//    }

}
