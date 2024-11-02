package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.dto.GreetingDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postRepository;

    @Override
    public Collection<Post> findAll() {
        Collection<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post findOne(Long id) {
        Post post = postRepository.getOne(id);
        return post;
    }

    @Override
    public Post create(PostDTO post) throws Exception {
        if (post.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Post savedPost = postRepository.save(new Post(post));
        return savedPost;
    }

    @Override
    @Transactional
    public Post update(PostDTO post, Long id) throws Exception {
        Post postToUpdate = findOne(id);
        if (postToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setLikesCount(post.getLikesCount());
        postToUpdate.setPublishingLocationId(post.getPublishingLocationId());
        postToUpdate.setPublishingDate(post.getPublishingDate());
        postToUpdate.setImage(post.getImage());
        postToUpdate.setComments(post.getComments());
        return postToUpdate;
    }

    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post deltePostById(Long id){
        return postRepository.deletePostById(id);
    }

}
