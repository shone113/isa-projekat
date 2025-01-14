package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.CommentDTO;
import rs.ac.uns.ftn.informatika.rest.repository.ICommentRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;
import rs.ac.uns.ftn.informatika.rest.repository.IUserRepository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private ProfileService profileService;

    public CommentService(){
        userService = new UserService();
        postService = new PostService();
    }

    public List<CommentDTO> findCommentsForPost(int postId) {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for( Comment comment : comments ) {
            if(comment.getPostId().equals(postId)) {
                CommentDTO commentDTO = new CommentDTO(comment);
                User user = userService.findById(comment.getCreatorId());
                commentDTO.setCreatorName(user.getName());
                commentDTO.setCreatorSurname(user.getSurname());
                commentDTOs.add(commentDTO);
            }
        }

        return commentDTOs;
    }

    @Transactional
    public CommentDTO create(CommentDTO commentDTO) {
        Post post = new Post(postService.findOne(commentDTO.getPostId()));
        User user = userService.findById(commentDTO.getCreatorId());
        Profile profile = profileService.getProfileByUserId(commentDTO.getCreatorId());
        Comment comment = new Comment(commentDTO);
        //comment.setCreator(user);
        comment.setCreator(profile);
        comment.setPost(post);
        comment.setCreationDate(LocalDate.now());
        Comment response = commentRepository.save(comment);
        CommentDTO responseDTO = new CommentDTO(response);
        responseDTO.setCreatorName(user.getName());
        responseDTO.setCreatorSurname(user.getSurname());
        return responseDTO;
    }

    @Transactional
    public Map<DayOfWeek, Integer> getWeeklyStatystic(){
        LocalDate oneWeekAgo = LocalDate.now().minusDays(6);;

        List<Comment> comments = commentRepository.findCommentsFromLastWeek(oneWeekAgo);

        LocalDate weekAgo = LocalDate.now().minusDays(6);
        DayOfWeek startDay = weekAgo.getDayOfWeek();

        // Inicijalizacija mape sa svim danima u nedelji, počevši od startDay
        Map<DayOfWeek, Integer> commentsPerDay = new LinkedHashMap<>();
        DayOfWeek currentDay = startDay;

        for(int i = 0; i <=6; i++){
            commentsPerDay.put(currentDay, 0);
            currentDay = currentDay.plus(1);
        }

        // Count comments per day
        for (Comment comment : comments) {
            DayOfWeek dayOfWeek = comment.getCreationDate().getDayOfWeek();
            commentsPerDay.put(dayOfWeek, commentsPerDay.get(dayOfWeek) + 1);
        }

        return commentsPerDay;
    }

    @Transactional
    public Map<Integer, Integer> getMonthlyStatystic(){
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate today = LocalDate.now();
        int daysInMonth = today.lengthOfMonth();

        // Dobavljamo komentare od prvog dana meseca do danas
        List<Comment> comments = commentRepository.findCommentsFromDateRange(startOfMonth, today);

        // Inicijalizujemo mapu sa svim danima u mesecu i početnom vrednošću 0 za svaki dan
        Map<Integer, Integer> commentsPerDay = new LinkedHashMap<>();
        for (int day = 1; day <= daysInMonth; day++) {
            commentsPerDay.put(day, 0);
        }

        // Brojimo komentare po danima
        for (Comment comment : comments) {
            int dayOfMonth = comment.getCreationDate().getDayOfMonth();
            commentsPerDay.put(dayOfMonth, commentsPerDay.get(dayOfMonth) + 1);
        }

        return commentsPerDay;
    }

    @Transactional
    public Map<Month, Integer> getYearlyStatystic() {
        LocalDate today = LocalDate.now();
        LocalDate startOfRange = today.minusMonths(11).withDayOfMonth(1);

        List<Comment> comments = commentRepository.findCommentsFromDateRange(startOfRange, today);

        Map<Month, Integer> commentsPerMonth = new LinkedHashMap<>();
        for (int i = 11; i >= 0; i--) {
            Month month = today.minusMonths(i).getMonth();
            commentsPerMonth.put(month, 0);
        }

        for (Comment comment : comments) {
            Month month = comment.getCreationDate().getMonth();
            if (commentsPerMonth.containsKey(month)) {
                commentsPerMonth.put(month, commentsPerMonth.get(month) + 1);
            }
        }

        return commentsPerMonth;
    }

}
