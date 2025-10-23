package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Image;
import com.example.demo.enums.ImageCategory;
import com.example.demo.service.ImageService;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // ==================== BASIC CRUD OPERATIONS ====================

    @GetMapping("/all")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Image>> getAllActiveImages() {
        List<Image> images = imageService.getAllActiveImages();
        return ResponseEntity.ok(images);
    }

    // ==================== COMBINED CREATE & GET OPERATIONS ====================

    @PostMapping(value = "/create-with-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> createImageWithFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("category") ImageCategory category,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy,
            @RequestParam(value = "altText", required = false) String altText) {

        try {
            Image uploadedImage = imageService.uploadImage(file, name, description, category, uploadedBy, altText);
            return ResponseEntity.ok(uploadedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}/with-file")
    public ResponseEntity<ImageWithFileResponse> getImageWithFile(@PathVariable UUID id) {
        try {
            Image image = imageService.getImageById(id);

            if (!image.isActive()) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(image.getFilePath());
            byte[] fileContent = Files.readAllBytes(filePath);
            String base64File = Base64.getEncoder().encodeToString(fileContent);

            ImageWithFileResponse response = new ImageWithFileResponse();
            response.setId(image.getId());
            response.setName(image.getName());
            response.setDescription(image.getDescription());
            response.setCategory(image.getCategory());
            response.setAltText(image.getAltText());
            response.setCreatedAt(image.getCreatedAt());
            response.setUpdatedAt(image.getUpdatedAt());
            response.setUploadedBy(image.getUploadedBy());
            response.setOriginalFileName(image.getOriginalFileName());
            response.setContentType(image.getContentType());
            response.setFileSize(image.getFileSize());
            response.setFileContent(base64File);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getImageFile(@PathVariable UUID id) {
        try {
            Image image = imageService.getImageById(id);

            if (!image.isActive()) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(image.getFilePath());
            byte[] fileContent = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(image.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getOriginalFileName() + "\"")
                    .body(fileContent);

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/serve/{id}")
    public ResponseEntity<byte[]> serveImageFile(@PathVariable UUID id) {
        return getImageFile(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable UUID id) {
        Image image = imageService.getImageById(id);
        return ResponseEntity.ok(image);
    }

    @PostMapping("/create")
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        Image createdImage = imageService.createImage(image);
        return ResponseEntity.ok(createdImage);
    }

    @PostMapping(value = "/create-with-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Image> createImageWithUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("category") ImageCategory category,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy,
            @RequestParam(value = "altText", required = false) String altText) {

        try {
            Image uploadedImage = imageService.uploadImage(file, name, description, category, uploadedBy, altText);
            return ResponseEntity.ok(uploadedImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // You might want to return a proper error response
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable UUID id, @RequestBody Image imageDetails) {
        Image updatedImage = imageService.updateImage(id, imageDetails);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok("Image deleted successfully");
    }

    // ==================== CATEGORY-BASED OPERATIONS ====================

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Image>> getImagesByCategory(@PathVariable ImageCategory category) {
        List<Image> images = imageService.getImagesByCategory(category);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/category/{category}/recent")
    public ResponseEntity<List<Image>> getRecentImagesByCategory(@PathVariable ImageCategory category) {
        List<Image> images = imageService.getImagesByCategoryOrderByCreatedAt(category);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/category/{category}/count")
    public ResponseEntity<Long> getImageCountByCategory(@PathVariable ImageCategory category) {
        Long count = imageService.getImageCountByCategory(category);
        return ResponseEntity.ok(count);
    }

    // ==================== SEARCH AND FILTERING ====================

    @GetMapping("/search")
    public ResponseEntity<List<Image>> searchImagesByName(@RequestParam String name) {
        List<Image> images = imageService.searchImagesByName(name);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/uploaded-by/{uploadedBy}")
    public ResponseEntity<List<Image>> getImagesByUploadedBy(@PathVariable String uploadedBy) {
        List<Image> images = imageService.getImagesByUploadedBy(uploadedBy);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Image>> getRecentImages(@RequestParam(defaultValue = "10") int limit) {
        List<Image> images = imageService.getRecentImages(limit);
        return ResponseEntity.ok(images);
    }

    // ==================== BULK OPERATIONS ====================

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<Image>> uploadMultipleImages(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("category") ImageCategory category,
            @RequestParam(value = "uploadedBy", required = false) String uploadedBy) {

        List<Image> uploadedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String name = file.getOriginalFilename();
                Image uploadedImage = imageService.uploadImage(file, name, null, category, uploadedBy, null);
                uploadedImages.add(uploadedImage);
            } catch (Exception e) {
                // Log error and continue with other files
                System.err.println("Failed to upload file: " + file.getOriginalFilename() + " - " + e.getMessage());
            }
        }

        return ResponseEntity.ok(uploadedImages);
    }

    // ==================== IMAGE CATEGORIES ====================

    @GetMapping("/categories")
    public ResponseEntity<ImageCategory[]> getImageCategories() {
        return ResponseEntity.ok(ImageCategory.values());
    }

    // ==================== STATISTICS ====================

//    @GetMapping("/stats")
//    public ResponseEntity<ImageStats> getImageStatistics() {
//        long totalImages = imageService.getAllActiveImages().size();
//
//        Map<ImageCategory, Long> categoryCounts = new HashMap<>();
//        for (ImageCategory category : ImageCategory.values()) {
//            categoryCounts.put(category, imageService.getImageCountByCategory(category));
//        }
//
//        ImageStats stats = new ImageStats();
//        stats.setTotalImages(totalImages);
//        stats.setCategoryCounts(categoryCounts);

    // DTO class for combined response with file content
   
    class ImageWithFileResponse {
        private UUID id;
        private String name;
        private String description;
        private ImageCategory category;
        private String altText;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String uploadedBy;
        private String originalFileName;
        private String contentType;
        private Long fileSize;
        private String fileContent; // Base64 encoded file content

        // Getters and setters
        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public ImageCategory getCategory() { return category; }
        public void setCategory(ImageCategory category) { this.category = category; }

        public String getAltText() { return altText; }
        public void setAltText(String altText) { this.altText = altText; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

        public String getUploadedBy() { return uploadedBy; }
        public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

        public String getOriginalFileName() { return originalFileName; }
        public void setOriginalFileName(String originalFileName) { this.originalFileName = originalFileName; }

        public String getContentType() { return contentType; }
        public void setContentType(String contentType) { this.contentType = contentType; }

        public Long getFileSize() { return fileSize; }
        public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

        public String getFileContent() { return fileContent; }
        public void setFileContent(String fileContent) { this.fileContent = fileContent; }
    }
}
//    // DTO class for statistics
//    public static class ImageStats {
//        private long totalImages;
//        private Map<ImageCategory, Long> categoryCounts;
//        public long getTotalImages() { return totalImages; }
//        public void setTotalImages(long totalImages) { this.totalImages = totalImages; }
//
//        public Map<ImageCategory, Long> getCategoryCounts() { return categoryCounts; }
//        public void setCategoryCounts(Map<ImageCategory, Long> categoryCounts) { this.categoryCounts = categoryCounts; }
//    }
//}
