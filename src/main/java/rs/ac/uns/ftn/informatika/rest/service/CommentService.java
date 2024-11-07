package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.repository.ICommentRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public CommentService(){
        userService = new UserService();
        postService = new PostService();
    }

    public List<Comment> findCommentsForPost(int postId) {
        List<Comment> comments = commentRepository.findAll();
        List<Comment> commentsForPost = new ArrayList<>();
        for( Comment comment : comments ) {
            if(comment.getPostId().equals(postId)) {
                commentsForPost.add(comment);
            }
        }
        return commentsForPost;
    }

    @Transactional
    public Comment create(CommentDTO commentDTO) {
        Post post = postService.findOne(commentDTO.getPostId());
        User user = userService.findById(commentDTO.getCreatorId());
        Comment comment = new Comment(commentDTO);
        comment.setCreator(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
