package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.ChatType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "messages")
@RequiredArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Store user references as UUIDs since User is no longer a JPA entity
    @Column(name = "sender_id", nullable = false)
    private UUID senderId;

    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String mediaUrl; // URL or path to media file
    private String mediaType; // e.g., image/jpeg, video/mp4

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    private boolean isRead = false;
    
    
    private ChatType type;
    
    
    
}
