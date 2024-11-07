package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.informatika.rest.domain.Post;

public interface IPostRepository extends JpaRepository<Post, Integer> {
    public Post deletePostById(Integer id);

}
