package com.example.demo.entity;

import com.example.demo.enums.ImageCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "images")
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageCategory category;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long fileSize;

    @Column
    private String uploadedBy; // Can be user ID or username

    @Column
    private String altText; // For accessibility
}
