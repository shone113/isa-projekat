package rs.ac.uns.ftn.informatika.rest.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.informatika.rest.service.ImageService;

@Tag(name="Image controller", description="controller for images")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            return imageService.saveImage(file);
        } catch (Exception e) {
            e.printStackTrace();
            return "Greska prilikom cuvanja fajla!";
        }
    }

}
