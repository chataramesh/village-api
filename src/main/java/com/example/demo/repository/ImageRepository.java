package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Image;
import com.example.demo.enums.ImageCategory;


public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findByIsActiveTrue();

    List<Image> findByCategory(ImageCategory category);

    List<Image> findByCategoryAndIsActiveTrue(ImageCategory category);

    @Query("SELECT i FROM Image i WHERE i.category = :category AND i.isActive = true ORDER BY i.createdAt DESC")
    List<Image> findByCategoryOrderByCreatedAtDesc(@Param("category") ImageCategory category);

    @Query("SELECT i FROM Image i WHERE i.name LIKE %:name% AND i.isActive = true")
    List<Image> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);

    @Query("SELECT i FROM Image i WHERE i.isActive = true ORDER BY i.createdAt DESC")
    List<Image> findAllActiveOrderByCreatedAtDesc();

    @Query("SELECT COUNT(i) FROM Image i WHERE i.category = :category AND i.isActive = true")
    Long countByCategoryAndIsActiveTrue(@Param("category") ImageCategory category);

    @Query("SELECT i FROM Image i WHERE i.uploadedBy = :uploadedBy AND i.isActive = true ORDER BY i.createdAt DESC")
    List<Image> findByUploadedByAndIsActiveTrueOrderByCreatedAtDesc(@Param("uploadedBy") String uploadedBy);
}
