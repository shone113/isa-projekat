package rs.ac.uns.ftn.informatika.rest.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Service
public class ImageCompressionService {

    private final String sourceDirectory = "src/main/resources/static/images";
    private final String compressedDirectory = "src/main/resources/static/compressed_images";

    // Ova metoda će se pokretati automatski svakog dana u 2 ujutru
    @Scheduled(cron = "0 * * * * *")
    public void compressOldImages() {
        System.out.println("Početak zadatka kompresije slika...");

        File sourceDir = new File(sourceDirectory);
        if (!sourceDir.exists()) {
            System.err.println("Izvorni direktorijum ne postoji: " + sourceDirectory);
            return;
        }

        try {
            Files.createDirectories(Paths.get(compressedDirectory));

            try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectory))) {
                paths.filter(Files::isRegularFile)
                        .forEach(this::processImageFile);
            }

        } catch (IOException e) {
            System.err.println("Došlo je do greške prilikom obrade fajlova: " + e.getMessage());
        }
        System.out.println("Zadatak kompresije je završen.");
    }

    private void processImageFile(Path filePath) {
        try {
            File imageFile = filePath.toFile();
            Instant fileCreationTime = ((FileTime) Files.getAttribute(filePath, "basic:creationTime")).toInstant();

            Path compressedImagePathAlready = Paths.get(compressedDirectory, imageFile.getName());

            if(Files.exists((compressedImagePathAlready))){
                System.out.println("Slika " + imageFile.getName() + " je vec kompresovana!");
                return;
            }

            if (fileCreationTime.isBefore(Instant.now().minus(3, ChronoUnit.DAYS))) {
                System.out.println("Kompresujem sliku: " + imageFile.getName());

                Path compressedImagePath = Paths.get(compressedDirectory, imageFile.getName());

                Thumbnails.of(imageFile)
                        .scale(1.0)
                        .outputQuality(0.6)
                        .toFile(compressedImagePath.toFile());

                System.out.println("Kompresovana slika sačuvana na: " + compressedImagePath);
            }
        } catch (IOException e) {
            System.err.println("Greška pri obradi slike " + filePath.getFileName() + ": " + e.getMessage());
        }
    }
}