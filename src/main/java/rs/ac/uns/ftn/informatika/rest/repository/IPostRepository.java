package rs.ac.uns.ftn.informatika.rest.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.rest.dto.ImageDTO;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;

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

    @Query(value = "SELECT COUNT(*) > 0 FROM post_likes WHERE post_id = :postId AND profile_id = :profileId", nativeQuery = true)
    boolean doesUserProfileLikedPost(@Param("profileId") Integer profileId, @Param("postId") Integer postId);

    @Query("SELECT p FROM Post p ORDER BY p.publishingDate DESC")
    List<Post> findAllPostsOrderByCreatedAtDesc();

    @Query("SELECT new rs.ac.uns.ftn.informatika.rest.dto.PostDTO(p) FROM Post p ORDER BY p.publishingDate DESC")
    List<PostDTO> findAllPostsWithoutImagesDesc();

    //    @Query("SELECT p.id, p.publishingDate, p.profile.id, p.description, p.likesCount, p.publishingLocationId FROM Post p ORDER BY p.publishingDate DESC")
//    List<PostDTO> findPostsWithoutImagesDesc();
    @Query("SELECT new rs.ac.uns.ftn.informatika.rest.dto.PostDTO(p) FROM Post p ORDER BY p.publishingDate DESC")
    List<PostDTO> findPostsWithoutImagesDesc();

    @Query("SELECT new rs.ac.uns.ftn.informatika.rest.dto.PostDTO(p) FROM Post p WHERE p.id = :postId")
    PostDTO findOnePostWithoutImage(@Param("postId") Integer postId);

    @Query("SELECT count(p) FROM Post  p")
    int countAllPosts();

    @Query("SELECT count(p) FROM Post p WHERE p.publishingDate > :date")
    int countPostsInLastMonth(@Param("date") LocalDate date);

    @Query("SELECT p FROM Post p ORDER BY p.likesCount DESC")
    List<Post> findMostLikedPosts(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.publishingDate > :date ORDER BY p.likesCount DESC")
    List<Post> findMostLikedPostsInLastWeek(@Param("date") LocalDate date, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.publishingDate >= :startDate")
    List<Post> findPostsFromLastWeek(@Param("startDate") LocalDate startDate);

    @Query("SELECT p FROM Post p WHERE p.publishingDate BETWEEN :startDate AND :endDate")
    List<Post> findPostsFromDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT new rs.ac.uns.ftn.informatika.rest.dto.ImageDTO(p.id, p.image, p.profile.id) FROM Post p ORDER BY p.publishingDate DESC")
    List<ImageDTO> findAllImages();

    @Query("SELECT new rs.ac.uns.ftn.informatika.rest.dto.ImageDTO(p.id, p.image, p.profile.id) FROM Post p WHERE p.id = :postId")
    ImageDTO findSingleImage(@Param("postId") Integer postId);
}
