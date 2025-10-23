package com.example.demo.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private UUID id;
    private UUID entityId;
    private String entityName;
    private String title;
    private String message;
    private String notificationType;
    private String priority;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledFor;
    private boolean isRead;
    private boolean isActive;
}
