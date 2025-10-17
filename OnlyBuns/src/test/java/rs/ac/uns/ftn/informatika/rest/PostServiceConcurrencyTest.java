package rs.ac.uns.ftn.informatika.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IProfileRepository;
import rs.ac.uns.ftn.informatika.rest.service.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.cache.type=NONE")
@ActiveProfiles("test")
@Rollback
public class PostServiceConcurrencyTest {

    @Autowired private PostService postService;
    @Autowired private IPostRepository postRepository;

    @Autowired private IProfileRepository profileRepository;

    @Test
    void testConcurrentLikes() throws InterruptedException {
        int n = 20;

        List<Profile> profiles = IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    User u = new User();
                    u.setName("Ime"+i);
                    u.setSurname("Prez"+i);
                    u.setEmail("test"+i+"@ex.com");
                    u.setPassword("pass");

                    Profile p = new Profile();
                    p.setUser(u);
                    return profileRepository.save(p);
                })
                .toList();

        Post post = new Post();
        post.setLikesCount(0);
        post.setDescription("lep zeka peka");
        post.setPublishingDate(LocalDateTime.now());
        post.setProfile(profiles.get(0));
        postRepository.save(post);

        Integer postId = post.getId();

        ExecutorService pool = Executors.newFixedThreadPool(n);
        IntStream.range(0, n).forEach(i ->
                pool.submit(() -> postService.likePost(postId, profiles.get(i).getId()))
        );
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        Post updated = postRepository.findById(postId).orElseThrow();
        assertEquals(n, updated.getLikesCount());
    }
}
