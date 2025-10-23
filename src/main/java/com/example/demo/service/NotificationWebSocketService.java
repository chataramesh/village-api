package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.NotificationResponse;

@Service
public class NotificationWebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * Send real-time notification to all subscribers of an entity
     */
    public void sendNotificationToEntitySubscribers(UUID entityId, String title, String message,
                                                   String notificationType, String priority, String entityName) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .entityId(entityId)
                .entityName(entityName)
                .title(title)
                .message(message)
                .notificationType(notificationType)
                .priority(priority)
                .isRead(false)
                .isActive(true)
                .build();

        // Broadcast to entity-specific topic for entity subscribers
        messagingTemplate.convertAndSend("/topic/entity/" + entityId + "/notifications", notificationResponse);
    }

    /**
     * Send real-time notification to a specific user
     */
    public void sendNotificationToUser(String userId, String title, String message, UUID entityId,
                                      String notificationType, String priority, String entityName) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .entityId(entityId)
                .entityName(entityName)
                .title(title)
                .message(message)
                .notificationType(notificationType)
                .priority(priority)
                .isRead(false)
                .isActive(true)
                .build();

        // Send to user's private queue
        messagingTemplate.convertAndSendToUser(userId, "/queue/notifications", notificationResponse);
    }

    /**
     * Send notification when user subscribes to an entity
     */
    public void sendSubscriptionNotification(UUID userId, UUID entityId, String entityName) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .entityId(entityId)
                .entityName(entityName)
                .title("Subscription Confirmed")
                .message("You have successfully subscribed to entity updates")
                .notificationType("SUBSCRIPTION")
                .priority("NORMAL")
                .isRead(false)
                .isActive(true)
                .build();

        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/notifications", notificationResponse);
    }

    /**
     * Send notification when user unsubscribes from an entity
     */
    public void sendUnsubscriptionNotification(UUID userId, UUID entityId, String entityName) {
        NotificationResponse notificationResponse = NotificationResponse.builder()
                .entityId(entityId)
                .entityName(entityName)
                .title("Unsubscription Confirmed")
                .message("You have successfully unsubscribed from entity updates")
                .notificationType("UNSUBSCRIPTION")
                .priority("NORMAL")
                .isRead(false)
                .isActive(true)
                .build();

        messagingTemplate.convertAndSendToUser(userId.toString(), "/queue/notifications", notificationResponse);
    }
}
