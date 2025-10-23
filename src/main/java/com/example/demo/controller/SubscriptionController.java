package com.example.demo.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.SubscriptionResponse;
import com.example.demo.entity.EntitySubscription;
import com.example.demo.entity.Entity_Item;
import com.example.demo.service.EntitySubscriptionService;
import com.example.demo.service.NotificationWebSocketService;



@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SubscriptionController {

    @Autowired
    private NotificationWebSocketService webSocketService;

    @Autowired
    private EntitySubscriptionService subscriptionService;

    /**
     * Subscribe to an entity
     */
    @PostMapping("/{entityId}/subscribe")
    public ResponseEntity<SubscriptionResponse> subscribeToEntity(
            @PathVariable UUID entityId,
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "GENERAL") String subscriptionType) {

        EntitySubscription subscription = subscriptionService.subscribeToEntity(userId, entityId, subscriptionType);

        // Send real-time subscription confirmation
        webSocketService.sendSubscriptionNotification(userId, entityId, subscription.getEntity().getName());

        SubscriptionResponse response = SubscriptionResponse.builder()
                .id(subscription.getId())
                .entityId(subscription.getEntity().getId())
                .entityName(subscription.getEntity().getName())
                .entityType(subscription.getEntity().getType())
                .subscriptionType(subscription.getSubscriptionType())
                .subscribedAt(subscription.getSubscribedAt())
                .isActive(subscription.isActive())
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * Unsubscribe from an entity
     */
    @DeleteMapping("/{entityId}/unsubscribe")
    public ResponseEntity<String> unsubscribeFromEntity(
            @PathVariable UUID entityId,
            @RequestParam UUID userId) {

        // Get subscription details before unsubscribing
        EntitySubscription subscription = subscriptionService.getEntitySubscribers(entityId)
                .stream()
                .filter(sub -> sub.getUser().getId().equals(userId))
                .findFirst()
                .orElse(null);

        subscriptionService.unsubscribeFromEntity(userId, entityId);

        // Send real-time unsubscription confirmation
        String entityName = subscription != null ? subscription.getEntity().getName() : "Unknown Entity";
        webSocketService.sendUnsubscriptionNotification(userId, entityId, entityName);

        return ResponseEntity.ok("Successfully unsubscribed from entity");
    }

    /**
     * Get user's subscriptions
     */
    @GetMapping("/my-subscriptions")
    public ResponseEntity<List<SubscriptionResponse>> getMySubscriptions(@RequestParam UUID userId) {
        List<EntitySubscription> subscriptions = subscriptionService.getUserSubscriptions(userId);

        List<SubscriptionResponse> responses = subscriptions.stream()
                .map(sub -> SubscriptionResponse.builder()
                        .id(sub.getId())
                        .entityId(sub.getEntity().getId())
                        .entityName(sub.getEntity().getName())
                        .entityType(sub.getEntity().getType())
                        .subscriptionType(sub.getSubscriptionType())
                        .subscribedAt(sub.getSubscribedAt())
                        .isActive(sub.isActive())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    /**
     * Get subscribers of an entity
     */
    @GetMapping("/{entityId}/subscribers")
    public ResponseEntity<List<String>> getEntitySubscribers(@PathVariable UUID entityId) {
        List<EntitySubscription> subscriptions = subscriptionService.getEntitySubscribers(entityId);

        List<String> subscriberNames = subscriptions.stream()
                .map(sub -> sub.getUser().getName())
                .collect(Collectors.toList());

        return ResponseEntity.ok(subscriberNames);
    }

    /**
     * Check if user is subscribed to entity
     */
    @GetMapping("/{entityId}/is-subscribed")
    public ResponseEntity<Boolean> isUserSubscribed(
            @PathVariable UUID entityId,
            @RequestParam UUID userId) {

        boolean isSubscribed = subscriptionService.isUserSubscribedToEntity(userId, entityId);
        return ResponseEntity.ok(isSubscribed);
    }

    /**
     * Get subscribed entities for a user
     */
    @GetMapping("/my-entities")
    public ResponseEntity<List<Entity_Item>> getMySubscribedEntities(@RequestParam UUID userId) {
        List<Entity_Item> entities = subscriptionService.getSubscribedEntities(userId);
        return ResponseEntity.ok(entities);
    }
}
