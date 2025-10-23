package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.entity.EntityNotification;
import com.example.demo.service.EntityNotificationService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    @Autowired
    private EntityNotificationService notificationService;

    /**
     * Get user's notifications
     */
    @GetMapping("/my-notifications")
    public ResponseEntity<List<NotificationResponse>> getMyNotifications(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<EntityNotification> notifications = notificationService.getUserNotifications(userId);

        List<NotificationResponse> responses = notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .entityId(notification.getEntity().getId())
                        .entityName(notification.getEntity().getName())
                        .title(notification.getTitle())
                        .message(notification.getMessage())
                        .notificationType(notification.getNotificationType())
                        .priority(notification.getPriority())
                        .createdAt(notification.getCreatedAt())
                        .scheduledFor(notification.getScheduledFor())
                        .isRead(false) // TODO: Implement read status tracking
                        .isActive(notification.isActive())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * Get notifications for a specific entity
     */
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<NotificationResponse>> getEntityNotifications(@PathVariable UUID entityId) {
        List<EntityNotification> notifications = notificationService.getEntityNotifications(entityId);

        List<NotificationResponse> responses = notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .entityId(notification.getEntity().getId())
                        .entityName(notification.getEntity().getName())
                        .title(notification.getTitle())
                        .message(notification.getMessage())
                        .notificationType(notification.getNotificationType())
                        .priority(notification.getPriority())
                        .createdAt(notification.getCreatedAt())
                        .scheduledFor(notification.getScheduledFor())
                        .isRead(false) // TODO: Implement read status tracking
                        .isActive(notification.isActive())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * Get unread notification count for user
     */
    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadNotificationCount(@RequestParam UUID userId) {
        List<EntityNotification> notifications = notificationService.getUserNotifications(userId);
        long unreadCount = notifications.size(); // TODO: Implement proper read status
        return ResponseEntity.ok(unreadCount);
    }

    /**
     * Mark notification as read
     */
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable UUID notificationId) {
        notificationService.markNotificationAsInactive(notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }

    /**
     * Send notification to entity subscribers (Admin only)
     */
    @PostMapping("/entity/{entityId}/broadcast")
    public ResponseEntity<String> broadcastToEntitySubscribers(
            @PathVariable UUID entityId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "GENERAL") String notificationType,
            @RequestParam(defaultValue = "NORMAL") String priority) {

        notificationService.sendNotificationToEntitySubscribers(entityId, title, message, notificationType, priority);
        return ResponseEntity.ok("Notification sent to all subscribers");
    }

    /**
     * Send notification to specific user (Admin only)
     */
    @PostMapping("/user/{userId}/send")
    public ResponseEntity<String> sendNotificationToUser(
            @PathVariable UUID userId,
            @RequestParam UUID entityId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam(defaultValue = "GENERAL") String notificationType,
            @RequestParam(defaultValue = "NORMAL") String priority) {

        notificationService.sendNotificationToUser(entityId, userId, title, message, notificationType, priority);
        return ResponseEntity.ok("Notification sent to user");
    }

    /**
     * Get recent notifications across all entities
     */
    @GetMapping("/recent")
    public ResponseEntity<List<NotificationResponse>> getRecentNotifications(
            @RequestParam(defaultValue = "24") int hours) {

        LocalDateTime fromDate = LocalDateTime.now().minusHours(hours);
        List<EntityNotification> notifications = notificationService.getRecentNotifications(fromDate);

        List<NotificationResponse> responses = notifications.stream()
                .map(notification -> NotificationResponse.builder()
                        .id(notification.getId())
                        .entityId(notification.getEntity().getId())
                        .entityName(notification.getEntity().getName())
                        .title(notification.getTitle())
                        .message(notification.getMessage())
                        .notificationType(notification.getNotificationType())
                        .priority(notification.getPriority())
                        .createdAt(notification.getCreatedAt())
                        .scheduledFor(notification.getScheduledFor())
                        .isRead(false)
                        .isActive(notification.isActive())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}
