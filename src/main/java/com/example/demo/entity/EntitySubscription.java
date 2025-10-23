package com.example.demo.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "entity_subscriptions")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntitySubscription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-subscriptions")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_id", nullable = false)
    @JsonBackReference(value = "entity-subscriptions")
    private Entity_Item entity;

    @Column(nullable = false)
    private LocalDateTime subscribedAt = LocalDateTime.now();

    @Column(nullable = false)
    private String subscriptionType = "GENERAL"; // GENERAL, EMERGENCY, UPDATES

    // Composite unique constraint to prevent duplicate subscriptions
    @Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "entity_id"})
    })
    public static class EntitySubscriptionConstraints {}
}
