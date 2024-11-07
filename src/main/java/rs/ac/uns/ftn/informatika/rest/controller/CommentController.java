package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.service.CommentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Tag(name = "Comment controller", description = "The comment API")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/by-post-id/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPost(@PathVariable int id) {
        List<Comment> comments = commentService.findCommentsForPost(id);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for(Comment comment : comments) {
            commentDTOs.add(new CommentDTO(comment));
        }
        return ResponseEntity.ok(commentDTOs);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        try{
            Comment savedComment = commentService.create(commentDTO);
            return new ResponseEntity<>(new CommentDTO(savedComment), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
