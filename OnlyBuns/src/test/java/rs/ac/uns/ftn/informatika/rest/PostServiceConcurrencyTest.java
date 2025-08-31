package rs.ac.uns.ftn.informatika.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.service.PostService;
import rs.ac.uns.ftn.informatika.rest.service.ProfileService;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostServiceConcurrencyTest {

    @Autowired
    private PostService postService;

    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ProfileService profileService;

    @Test
    public void testConcurrentLikes() throws InterruptedException {
        Post post = new Post();
        post.setLikesCount(0);
        post.setDescription("lep zeka peka");
        post.setPublishingDate(LocalDateTime.now());
        Profile profile = profileService.getProfileById(4);
        post.setProfile(profile);
        postRepository.save(post);

        Integer postId = post.getId();
        int numberOfUsers = 100;

        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Simulišemo 100 korisnika koji lajkuju isti post
        IntStream.range(0, numberOfUsers).forEach(i ->
                executor.submit(() -> postService.likePost(postId, 1))
        );

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Ponovo učitaj post da dobiješ najnoviju vrednost
        Post updatedPost = postRepository.findById(postId).get();

        // Proveri da li je konačan broj lajkova tačan
        assertEquals(numberOfUsers, updatedPost.getLikesCount());
    }
}