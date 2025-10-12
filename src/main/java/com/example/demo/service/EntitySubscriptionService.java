package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.EntitySubscription;
import com.example.demo.entity.Entity_Item;
import com.example.demo.entity.User;
import com.example.demo.repository.EntitySubscriptionRepository;
import com.example.demo.repository.EntityItemRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class EntitySubscriptionService {

    @Autowired
    private EntitySubscriptionRepository subscriptionRepository;

    @Autowired
    private EntityItemRepository entityRepository;

    @Autowired
    private UserRepository userRepository;

    public EntitySubscription subscribeToEntity(UUID userId, UUID entityId, String subscriptionType) {
        // Check if user exists and is active
        User user = userRepository.findByIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new RuntimeException("Active user not found with id: " + userId));

        // Check if entity exists and is active
        Entity_Item entity = entityRepository.findByIdAndIsActiveTrue(entityId)
                .orElseThrow(() -> new RuntimeException("Active entity not found with id: " + entityId));

        // Check if already subscribed
        if (subscriptionRepository.existsByUserIdAndEntityIdAndIsActiveTrue(userId, entityId)) {
            throw new RuntimeException("User is already subscribed to this entity");
        }

        // Create new subscription
        EntitySubscription subscription = new EntitySubscription();
        subscription.setUser(user);
        subscription.setEntity(entity);
        subscription.setSubscriptionType(subscriptionType != null ? subscriptionType : "GENERAL");
        subscription.setActive(true);

        return subscriptionRepository.save(subscription);
    }

    public void unsubscribeFromEntity(UUID userId, UUID entityId) {
        EntitySubscription subscription = subscriptionRepository
                .findActiveByUserAndEntity(userId, entityId)
                .orElseThrow(() -> new RuntimeException("Active subscription not found"));

        subscription.setActive(false);
        subscriptionRepository.save(subscription);
    }

    public List<EntitySubscription> getUserSubscriptions(UUID userId) {
        return subscriptionRepository.findActiveSubscriptionsByUser(userId);
    }

    public List<EntitySubscription> getEntitySubscribers(UUID entityId) {
        return subscriptionRepository.findActiveSubscriptionsByEntity(entityId);
    }

    public List<EntitySubscription> getCrossVillageSubscriptions(UUID villageId) {
        return subscriptionRepository.findCrossVillageSubscriptions(villageId);
    }

    public boolean isUserSubscribedToEntity(UUID userId, UUID entityId) {
        return subscriptionRepository.existsByUserIdAndEntityIdAndIsActiveTrue(userId, entityId);
    }

    public List<Entity_Item> getSubscribedEntities(UUID userId) {
        List<EntitySubscription> subscriptions = getUserSubscriptions(userId);
        return subscriptions.stream()
                .map(EntitySubscription::getEntity)
                .toList();
    }

    public List<User> getEntitySubscribers(UUID entityId) {
        List<EntitySubscription> subscriptions = getEntitySubscribers(entityId);
        return subscriptions.stream()
                .map(EntitySubscription::getUser)
                .toList();
    }
}
