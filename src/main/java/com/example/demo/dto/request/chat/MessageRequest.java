package com.example.demo.dto.request.chat;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.demo.enums.ChatType;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MessageRequest {
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
    
    private String token;
}
