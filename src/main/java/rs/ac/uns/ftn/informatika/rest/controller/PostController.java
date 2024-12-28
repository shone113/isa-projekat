package rs.ac.uns.ftn.informatika.rest.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.service.PostService;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import org.springframework.security.access.prepost.PreAuthorize;


import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;

@Tag(name = "Post controller", description = "The posting API")
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Post>> getPosts(HttpSession httpSession){
        Collection<Post> posts = postService.findAll();
        return new ResponseEntity<Collection<Post>>(posts, HttpStatus.OK);
    }

    @GetMapping(value="/all-for-logged-user",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PostDTO>> getPostsForLoggedUser(HttpSession httpSession){
        Authentication token = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) token.getPrincipal();
        List<PostDTO> postDTOs = postService.findAllForLoggedUser(user.getId());
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/for-user/{id}")
        public ResponseEntity<List<PostDTO>> getPostsForUser(@PathVariable Integer id){
        List<PostDTO> postDTOs = postService.findPostsForUser(id);
        return ResponseEntity.ok(postDTOs);
    }

//    @PatchMapping(value = "/like/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PostDTO> likePost(@PathVariable Integer postId, @RequestParam Integer profileId){
//        Post post = postService.likePost(postId, profileId);
//        return ResponseEntity.ok(new PostDTO(post));
//    }
    @PutMapping(value = "/like/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostDTO> likePost(@PathVariable Integer postId, @RequestParam Integer profileId){
        PostDTO postDTO = postService.likePost(postId, profileId);
        return ResponseEntity.ok(postDTO);
    }

    @PutMapping(value = "/unlike/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostDTO> unlikePost(@PathVariable Integer postId, @RequestParam Integer profileId){
        PostDTO postDTO = postService.unlikePost(postId, profileId);
        return ResponseEntity.ok(postDTO);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> createPost(@RequestBody PostDTO post){
        Post savedPost = null;
        try{
            savedPost = postService.create(post);
            return new ResponseEntity<Post>(savedPost, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<Post>(savedPost, HttpStatus.CONFLICT);
        }
    }

    @PutMapping(value="/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO post, @PathVariable int id){
        try{
            Authentication token = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) token.getPrincipal();
            Post updatedPost = postService.update(post, id, user.getId());
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Post> deletePost(@Parameter(description = "Post id", required = true) @PathVariable("id") int postId){
        try{
            Authentication token = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) token.getPrincipal();
            postService.delete(postId, user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total-number-of-posts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> getTotalNumberOfPosts(){
        int result = postService.totalNumberOfPosts();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/total-number-of-posts-last-month")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> getTotalNumberOfPostsInLastMonth(){
        int result = postService.totalNumberOfPostsInLastMonth();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/most-popular-posts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Post>> getMostPopularPosts(){
        List<Post> result = postService.mostPopularPosts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/most-popular-posts-last-week")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Post>> getMostPopularPostsInlastWeek(){
        List<Post> result = postService.mostPopularPostsInLastWeek();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/analytics/weekly")
    public  ResponseEntity<Map<DayOfWeek, Integer>> getWeeklyStatystic() {
        Map<DayOfWeek, Integer> weeklyStatystic = postService.getWeeklyStatystic();
        return ResponseEntity.ok(weeklyStatystic);
    }

    @GetMapping("/analytics/monthly")
    public  ResponseEntity<Map<Integer, Integer>> getMonthlyStatystic() {
        Map<Integer, Integer> monthlyStatystic = postService.getMonthlyStatystic();
        return ResponseEntity.ok(monthlyStatystic);
    }

    @GetMapping("/analytics/yearly")
    public  ResponseEntity<Map<Month, Integer>> getYearlyStatystic() {
        Map<Month, Integer> yearlyStatystic = postService.getYearlyStatystic();
        return ResponseEntity.ok(yearlyStatystic);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
