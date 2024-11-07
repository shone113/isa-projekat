package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.rest.domain.Greeting;
import rs.ac.uns.ftn.informatika.rest.domain.Post;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class PostRepository{
    private final IPostRepository postRepository;
    private final ConcurrentMap<Integer, Post> posts = new ConcurrentHashMap<Integer, Post>();

    public PostRepository(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post deletePostById(Integer id){
        Post post = this.posts.remove(id);
        return post;
    }
}

