package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.EntitySubscription;

public interface EntitySubscriptionRepository extends JpaRepository<EntitySubscription, UUID> {

    Optional<EntitySubscription> findByUserIdAndEntityId(UUID userId, UUID entityId);

    List<EntitySubscription> findByUserId(UUID userId);

    List<EntitySubscription> findByEntityId(UUID entityId);

    List<EntitySubscription> findByUserIdAndIsActiveTrue(UUID userId);

    List<EntitySubscription> findByEntityIdAndIsActiveTrue(UUID entityId);

    @Query("SELECT es FROM EntitySubscription es WHERE es.user.id = :userId AND es.entity.id = :entityId AND es.isActive = true")
    Optional<EntitySubscription> findActiveByUserAndEntity(@Param("userId") UUID userId, @Param("entityId") UUID entityId);

    @Query("SELECT es FROM EntitySubscription es WHERE es.user.id = :userId AND es.isActive = true")
    List<EntitySubscription> findActiveSubscriptionsByUser(@Param("userId") UUID userId);

    @Query("SELECT es FROM EntitySubscription es WHERE es.entity.id = :entityId AND es.isActive = true")
    List<EntitySubscription> findActiveSubscriptionsByEntity(@Param("entityId") UUID entityId);

    @Query("SELECT es FROM EntitySubscription es WHERE es.user.village.id = :villageId AND es.entity.village.id != :villageId AND es.isActive = true")
    List<EntitySubscription> findCrossVillageSubscriptions(@Param("villageId") UUID villageId);

    boolean existsByUserIdAndEntityIdAndIsActiveTrue(UUID userId, UUID entityId);
}
