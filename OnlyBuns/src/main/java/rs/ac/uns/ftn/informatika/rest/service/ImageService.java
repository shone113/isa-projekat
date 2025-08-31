package rs.ac.uns.ftn.informatika.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.informatika.rest.domain.Post;
import rs.ac.uns.ftn.informatika.rest.dto.ImageDTO;
import rs.ac.uns.ftn.informatika.rest.repository.IPostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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


    private final String UPLOAD_DIR = "src/main/resources/static/images";

    public String saveImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + fileName;
    }

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
