package com.example.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.max-file-size}")
    private String maxFileSize;

    @Value("${app.upload.allowed-extensions}")
    private String allowedExtensions;

    public String getUploadDir() {
        return uploadDir;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public List<String> getAllowedExtensions() {
        return Arrays.asList(allowedExtensions.split(","));
    }

    public boolean isAllowedExtension(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return false;
        }

        String extension = getFileExtension(filename).toLowerCase();
        return getAllowedExtensions().contains(extension);
    }

    public boolean isValidImageFile(String contentType) {
        return contentType != null && contentType.startsWith("image/");
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex >= 0 ? filename.substring(lastDotIndex + 1) : "";
    }

    public long getMaxFileSizeInBytes() {
        return parseFileSize(maxFileSize);
    }

    private long parseFileSize(String fileSize) {
        if (fileSize == null || fileSize.trim().isEmpty()) {
            return 10 * 1024 * 1024; // Default 10MB
        }

        String size = fileSize.trim().toUpperCase();
        long multiplier = 1;

        if (size.endsWith("KB")) {
            multiplier = 1024;
            size = size.substring(0, size.length() - 2);
        } else if (size.endsWith("MB")) {
            multiplier = 1024 * 1024;
            size = size.substring(0, size.length() - 2);
        } else if (size.endsWith("GB")) {
            multiplier = 1024 * 1024 * 1024;
            size = size.substring(0, size.length() - 2);
        }

        try {
            return Long.parseLong(size.trim()) * multiplier;
        } catch (NumberFormatException e) {
            return 10 * 1024 * 1024; // Default 10MB
        }
    }
}
