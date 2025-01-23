package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface IPostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :postId")
    public void deletePostById(@Param("postId") Integer postId);

    @Modifying
    @Query(value = "INSERT INTO post_likes (post_id, profile_id) VALUES (:postId, :profileId)", nativeQuery = true)
    void likePost(@Param("postId") Integer postId, @Param("profileId") Integer profileId);

    @Modifying
    @Query(value = "DELETE FROM post_likes WHERE post_id = :postId AND profile_id = :profileId", nativeQuery = true)
    void unlikePost(@Param("postId") Integer postId, @Param("profileId") Integer profileId);


}
