package rs.ac.uns.ftn.informatika.rest.service;

import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;

import java.util.Collection;

public interface IPostService {
    Collection<Post> findAll();
    Post findOne(Long id);
    Post create(PostDTO post) throws Exception;
    Post update(PostDTO post, Long id) throws Exception;
    void delete(Long id);
    Post deltePostById(Long id);
}
