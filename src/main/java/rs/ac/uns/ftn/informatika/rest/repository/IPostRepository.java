package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Post;

import javax.transaction.Transactional;

public interface IPostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    public void deletePostById(@Param("postId") Integer postId);

}
