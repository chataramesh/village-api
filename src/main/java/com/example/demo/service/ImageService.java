package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.FileUploadConfig;
import com.example.demo.entity.Image;
import com.example.demo.enums.ImageCategory;
import com.example.demo.repository.ImageRepository;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileUploadConfig fileUploadConfig;

    public Image createImage(Image image) {
        return imageRepository.save(image);
    }

    public Image uploadImage(MultipartFile file, String name, String description,
                           ImageCategory category, String uploadedBy, String altText) throws IOException {

        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("Cannot upload empty file");
        }

        // Validate file size
        if (file.getSize() > fileUploadConfig.getMaxFileSizeInBytes()) {
            throw new RuntimeException("File size exceeds maximum allowed size");
        }

        // Validate file type (only images)
        String contentType = file.getContentType();
        if (!fileUploadConfig.isValidImageFile(contentType)) {
            throw new RuntimeException("Only image files are allowed");
        }

        // Validate file extension
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!fileUploadConfig.isAllowedExtension(originalFileName)) {
            throw new RuntimeException("File extension not allowed");
        }

        // Create unique filename
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + "." + fileExtension;

        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(fileUploadConfig.getUploadDir()).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // Save file
        Path targetLocation = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // Create image entity
        Image image = new Image();
        image.setName(name != null ? name : originalFileName);
        image.setDescription(description);
        image.setCategory(category);
        image.setFilePath(targetLocation.toString());
        image.setOriginalFileName(originalFileName);
        image.setContentType(contentType);
        image.setFileSize(file.getSize());
        image.setUploadedBy(uploadedBy);
        image.setAltText(altText);

        return imageRepository.save(image);
    }

    public Image getImageById(UUID id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public List<Image> getAllActiveImages() {
        return imageRepository.findByIsActiveTrue();
    }

    public List<Image> getImagesByCategory(ImageCategory category) {
        return imageRepository.findByCategoryAndIsActiveTrue(category);
    }

    public List<Image> getImagesByCategoryOrderByCreatedAt(ImageCategory category) {
        return imageRepository.findByCategoryOrderByCreatedAtDesc(category);
    }

    public List<Image> searchImagesByName(String name) {
        return imageRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    public List<Image> getImagesByUploadedBy(String uploadedBy) {
        return imageRepository.findByUploadedByAndIsActiveTrueOrderByCreatedAtDesc(uploadedBy);
    }

    public Image updateImage(UUID id, Image imageDetails) {
        Image image = getImageById(id);

        if (imageDetails.getName() != null) {
            image.setName(imageDetails.getName());
        }
        if (imageDetails.getDescription() != null) {
            image.setDescription(imageDetails.getDescription());
        }
        if (imageDetails.getCategory() != null) {
            image.setCategory(imageDetails.getCategory());
        }
        if (imageDetails.getAltText() != null) {
            image.setAltText(imageDetails.getAltText());
        }
        image.setActive(imageDetails.isActive());

        return imageRepository.save(image);
    }

    public void deleteImage(UUID id) {
        Image image = getImageById(id);

        // Delete physical file
        try {
            Path filePath = Paths.get(image.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but don't fail the deletion
            System.err.println("Failed to delete file: " + image.getFilePath());
        }

        imageRepository.delete(image);
    }

    public Long getImageCountByCategory(ImageCategory category) {
        return imageRepository.countByCategoryAndIsActiveTrue(category);
    }

    public List<Image> getRecentImages(int limit) {
        return imageRepository.findAllActiveOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .toList();
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex >= 0 ? filename.substring(lastDotIndex + 1).toLowerCase() : "";
    }

    public boolean isValidImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public String getImageUrl(Image image) {
        // Return URL to serve the actual image file
        return "/api/images/" + image.getId() + "/file";
    }

    public String getImageServeUrl(Image image) {
        // Alternative URL for serving images
        return "/api/images/serve/" + image.getId();
    }
}
