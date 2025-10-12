package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.EntityNotification;

public interface EntityNotificationRepository extends JpaRepository<EntityNotification, UUID> {

    List<EntityNotification> findByEntityId(UUID entityId);

    List<EntityNotification> findByEntityIdAndIsActiveTrue(UUID entityId);

    List<EntityNotification> findByNotificationType(String notificationType);

    List<EntityNotification> findByPriority(String priority);

    @Query("SELECT en FROM EntityNotification en WHERE en.entity.id = :entityId AND en.isActive = true ORDER BY en.createdAt DESC")
    List<EntityNotification> findActiveByEntityOrderByCreatedAtDesc(@Param("entityId") UUID entityId);

    @Query("SELECT en FROM EntityNotification en WHERE en.createdAt >= :fromDate AND en.isActive = true ORDER BY en.createdAt DESC")
    List<EntityNotification> findRecentNotifications(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT en FROM EntityNotification en WHERE en.scheduledFor <= :currentTime AND en.isActive = true ORDER BY en.scheduledFor ASC")
    List<EntityNotification> findScheduledNotificationsReadyToSend(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT DISTINCT en FROM EntityNotification en JOIN en.recipients r WHERE r.id = :userId ORDER BY en.createdAt DESC")
    List<EntityNotification> findNotificationsForUser(@Param("userId") UUID userId);

    @Query("SELECT COUNT(r) FROM EntityNotification en JOIN en.recipients r WHERE en.id = :notificationId")
    Long countRecipients(@Param("notificationId") UUID notificationId);
}
