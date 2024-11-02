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
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.service.PostService;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Tag(name = "Post controller", description = "The posting API")
@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(description = "Get all posts", method = "GET")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Post>> getPosts(HttpSession httpSession){
        Collection<Post> posts = postService.findAll();
        return new ResponseEntity<Collection<Post>>(posts, HttpStatus.OK);
    }

    @Operation(description = "Creating new post", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "409", description = "Not possible to create new greeting when given id is not null or empty",
                    content = @Content)
    })
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

    @Operation(description = "Updates an existing greeting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post successfully edited",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}
            ),
            @ApiResponse(responseCode = "404", description = "Post not found",
                content = @Content)
        })
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> updatePost(@RequestBody PostDTO post, @PathVariable long id){
        try{
            Post updatedPost = postService.update(post, id);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(description = "Delete a post", method = "DELETE")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Post not found",
        content = @Content),
    @ApiResponse(responseCode = "204", description = "Post successfully deleted",
        content = @Content)})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Post> deletePost(@Parameter(description = "Post id", required = true) @PathVariable("id") long id){
        Post deletedPost = postService.deltePostById(id);
        if(deletedPost == null){
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Post>(HttpStatus.OK);
    }
}
