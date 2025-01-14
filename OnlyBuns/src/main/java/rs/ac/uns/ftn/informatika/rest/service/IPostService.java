package rs.ac.uns.ftn.informatika.rest.service;

import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;

import java.util.Collection;
import java.util.List;

public interface IPostService {
    List<PostDTO> findAll();

    PostDTO findOne(Integer id);

    Post create(PostDTO post) throws Exception;

    Post update(PostDTO post, Integer postId, Integer userId) throws Exception;

    //    void delete(Integer id);
//    Post deltePostById(Integer id);
    void delete(Integer postId, Integer creatorUserId) throws Exception;
}