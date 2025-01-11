package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.dto.ImageDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ProfileService profileService;


//    @Cacheable(value = "image", key = "#userId")
//    public List<String> findPhotosForUser(Integer userId) {
//        List<Post> posts = postRepository.findAllPostsOrderByCreatedAtDesc();
//        List<String> photos = new ArrayList<>();
//
//        for (Post post : posts) {
//            if (profileService.doesFollowPublisher(userId, post.getCreatorProfileId())) {
//                photos.add(post.getImage()); // Pretpostavljam da je `getPhotos()` lista URL-ova slika
//            }
//        }
//        System.out.println("Fetching photos for user ID: " + userId); // Log za proveru keša
//        return photos;
//    }

    @Cacheable(value = "image", key = "#userId")
    public Map<Integer, String> findPhotosForUser(Integer userId) {
        List<ImageDTO> images = postRepository.findAllImages();
        Map<Integer, String> postIdToImageMap = new HashMap<>();

        for (ImageDTO image : images) {
            if (profileService.doesFollowPublisher(userId, image.getCreatorProfileId())) {
                postIdToImageMap.put(image.getPostId(), image.getImage()); // Čuvanje ID-a posta kao ključa i URL slike kao vrednosti
            }
        }

        System.out.println("Fetching photos for user ID: " + userId); // Log za proveru keša
        return postIdToImageMap;
    }

    @Cacheable(value = "image", key = "'allPhotos'")
    public Map<Integer, String> findAllPhotos() {
        List<ImageDTO> images = postRepository.findAllImages();
        Map<Integer, String> postIdToImageMap = new HashMap<>();

        for (ImageDTO image : images) {
            postIdToImageMap.put(image.getPostId(), image.getImage());
        }

        System.out.println("Fetching all photos of posts count: " + postIdToImageMap.size()); // Log za proveru keša
        return postIdToImageMap;
    }

    @Cacheable(value = "image", key = "#postId")
    public String findPhotoForPost(Integer postId) {
        ImageDTO imageDTO = postRepository.findSingleImage(postId);
        Map<Integer, String> postIdToImageMap = new HashMap<>();

        postIdToImageMap.put(imageDTO.getPostId(), imageDTO.getImage());

        System.out.println("Fetching single photo, postId: " + imageDTO.getPostId()); // Log za proveru keša
        return imageDTO.getImage();
    }


}
