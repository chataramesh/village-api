package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "entity_subscriptions")
@Data
public class EntitySubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    private Entity_Item entity;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime subscribedAt = LocalDateTime.now();

    private String subscriptionType = "GENERAL"; // GENERAL, EMERGENCY, UPDATES

    // Composite unique constraint to prevent duplicate subscriptions
    @Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "entity_id"})
    })
    public static class EntitySubscriptionConstraints {}
}
