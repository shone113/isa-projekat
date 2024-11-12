package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.headers.Header;
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
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.service.CommentService;
import rs.ac.uns.ftn.informatika.rest.service.UserService;
import rs.ac.uns.ftn.informatika.rest.util.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Tag(name = "Comment controller", description = "The comment API")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;
    @GetMapping("/by-post-id/{id}")
    public ResponseEntity<List<CommentDTO>> getCommentsForPost(@PathVariable int id) {
        List<CommentDTO> commentDTOs = commentService.findCommentsForPost(id);
        return ResponseEntity.ok(commentDTOs);
    }
//    @PreAuthorize("hasRole("USER")")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        try{
            Authentication token = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) token.getPrincipal();
            commentDTO.setCreatorId(user.getId());
            CommentDTO savedComment = commentService.create(commentDTO);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
