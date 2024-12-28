package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Comment;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.domain.Profile;
import rs.ac.uns.ftn.informatika.rest.domain.User;
import rs.ac.uns.ftn.informatika.rest.dto.PostDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @Override
    public Collection<Post> findAll() {
        Collection<Post> posts = postRepository.findAllPostsOrderByCreatedAtDesc();
        return posts;
    }

    public List<PostDTO> findAllForLoggedUser(Integer userId) {
        List<Post> posts = postRepository.findAllPostsOrderByCreatedAtDesc();
        User user = userService.findById(userId);
        List<PostDTO> filteredPostDTOs = new ArrayList<>();
        for(Post post : posts) {
            PostDTO postDTO = new PostDTO(post);
            Profile profile = profileService.getProfileByUserId(userId);
            postDTO.setLiked(postRepository.doesUserProfileLikedPost(profile.getId(), post.getId()));
            postDTO.setCreatorName(user.getName());
            postDTO.setCreatorSurname(user.getSurname());
            filteredPostDTOs.add(postDTO);
        }

        return filteredPostDTOs;
    }

    @Override
    public Post findOne(Integer id) {
        Post post = postRepository.getOne(id);
        return post;
    }

    @Transactional
    public List<PostDTO> findPostsForUser(Integer userId) {
        List<Post> posts = postRepository.findAllPostsOrderByCreatedAtDesc();
        User user = userService.findById(userId);
        List<PostDTO> filteredPostDTOs = new ArrayList<>();
        for(Post post : posts) {
            if(profileService.doesFollowPublisher(userId, post.getCreatorProfileId())){
                PostDTO postDTO = new PostDTO(post);
                Profile profile = profileService.getProfileByUserId(userId);
                postDTO.setLiked(postRepository.doesUserProfileLikedPost(profile.getId(), post.getId()));
                postDTO.setCreatorName(user.getName());
                postDTO.setCreatorSurname(user.getSurname());
                filteredPostDTOs.add(postDTO);
            }
        }

        return filteredPostDTOs;
    }

    @Override
    public Post create(PostDTO post) throws Exception {
        if (post.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Post savedPost = postRepository.save(new Post(post));
        return savedPost;
    }

    @Transactional
    public PostDTO likePost(int postId, int profileId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        postRepository.likePost(postId, profileId);

        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
        PostDTO postDTO = new PostDTO(post);
        postDTO.setLiked(true);
        return postDTO;
    }


    @Transactional
    public PostDTO unlikePost(int postId, int profileId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId));

        postRepository.unlikePost(postId, profileId);

        post.setLikesCount(post.getLikesCount() - 1);
        postRepository.save(post);
        PostDTO postDTO = new PostDTO(post);
        postDTO.setLiked(false);
        return postDTO;
    }

    @Transactional
    public Post update(PostDTO post, Integer postId, Integer userId) throws Exception {
        Post postToUpdate = findOne(postId);
        if (postToUpdate == null) {
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        Integer creatorProfileId = profileService.getProfileByUserId(userId).getId();
        if(postToUpdate.getCreatorProfileId() != creatorProfileId){
            throw new Exception("Unauthenticated user");
        }

        postToUpdate.setDescription(post.getDescription());
        postToUpdate.setLikesCount(post.getLikesCount());
//        postToUpdate.setPublishingLocationId(post.getPublishingLocationId());
        postToUpdate.setPublishingDate(post.getPublishingDate());
        postToUpdate.setImage(post.getImage());
        postToUpdate.setComments(post.getComments());
        return postToUpdate;
    }

    public void delete(Integer postId, Integer creatorUserId) {
        Integer creatorProfileId = profileService.getProfileByUserId(creatorUserId).getId();
        Post post = postRepository.findById(postId).get();
        if(post.getCreatorProfileId() == creatorProfileId){
            postRepository.deleteById(postId);
        }
    }

    public Integer totalNumberOfPosts() {
        return postRepository.countAllPosts();
    }

    public int totalNumberOfPostsInLastMonth(){
        LocalDate date = LocalDate.now().minusMonths(1);
        return postRepository.countPostsInLastMonth(date);
    }

    public List<Post> mostPopularPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        return postRepository.findMostLikedPosts(pageable);
    }

    public List<Post> mostPopularPostsInLastWeek() {
        Pageable pageable = PageRequest.of(0, 5);
        LocalDate date = LocalDate.now().minusDays(7);
        return postRepository.findMostLikedPostsInLastWeek(date, pageable);
    }


    @Transactional
    public Map<DayOfWeek, Integer> getWeeklyStatystic(){
        LocalDate oneWeekAgo = LocalDate.now().minusDays(6);;

        List<Post> posts = postRepository.findPostsFromLastWeek(oneWeekAgo);

        LocalDate weekAgo = LocalDate.now().minusDays(6);
        DayOfWeek startDay = weekAgo.getDayOfWeek();

        // Inicijalizacija mape sa svim danima u nedelji, počevši od startDay
        Map<DayOfWeek, Integer> commentsPerDay = new LinkedHashMap<>();
        DayOfWeek currentDay = startDay;

        for(int i = 0; i <=6; i++){
            commentsPerDay.put(currentDay, 0);
            currentDay = currentDay.plus(1);
        }

        for (Post post : posts) {
            DayOfWeek dayOfWeek = post.getPublishingDate().getDayOfWeek();
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
        List<Post> posts = postRepository.findPostsFromDateRange(startOfMonth, today);

        // Inicijalizujemo mapu sa svim danima u mesecu i početnom vrednošću 0 za svaki dan
        Map<Integer, Integer> postsPerDay = new LinkedHashMap<>();
        for (int day = 1; day <= daysInMonth; day++) {
            postsPerDay.put(day, 0);
        }

        // Brojimo komentare po danima
        for (Post post : posts) {
            int dayOfMonth = post.getPublishingDate().getDayOfMonth();
            postsPerDay.put(dayOfMonth, postsPerDay.get(dayOfMonth) + 1);
        }

        return postsPerDay;
    }

    @Transactional
    public Map<Month, Integer> getYearlyStatystic() {
        LocalDate today = LocalDate.now();
        LocalDate startOfRange = today.minusMonths(11).withDayOfMonth(1);

        List<Post> posts = postRepository.findPostsFromDateRange(startOfRange, today);

        Map<Month, Integer> postsPerMonth = new LinkedHashMap<>();
        for (int i = 11; i >= 0; i--) {
            Month month = today.minusMonths(i).getMonth();
            postsPerMonth.put(month, 0);
        }

        for (Post post : posts) {
            Month month = post.getPublishingDate().getMonth();
            if (postsPerMonth.containsKey(month)) {
                postsPerMonth.put(month, postsPerMonth.get(month) + 1);
            }
        }
        return postsPerMonth;
    }
}
