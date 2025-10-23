package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.service.EntityAuditListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
// @EntityListeners(EntityAuditListener.class) // Temporarily disabled to debug StackOverflowError
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = true)
    private LocalDateTime createdAt;

   
    @JoinColumn(name = "created_by")
    private String createdBy;

    private LocalDateTime updatedAt;


    @JoinColumn(name = "updated_by")
    private String updatedBy;
    

//    @Column(nullable = false)
    private boolean isActive = true;

}
