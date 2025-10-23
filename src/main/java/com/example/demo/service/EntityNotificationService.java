package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.EntityNotification;
import com.example.demo.entity.EntitySubscription;
import com.example.demo.entity.Entity_Item;
import com.example.demo.repository.EntityNotificationRepository;
import com.example.demo.repository.EntitySubscriptionRepository;

import jakarta.mail.MessagingException;

@Service
@Transactional
public class EntityNotificationService {

	@Autowired
	private NotificationWebSocketService webSocketService;

	@Autowired
	private EntitySubscriptionService subscriptionService;

	@Autowired
	private EntityNotificationRepository notificationRepository;

	@Autowired
	private EntitySubscriptionRepository subscriptionRepository;

	@Autowired
	private EmailService emailService;

//	@Autowired
//	private SMSService smsService;

	public EntityNotification createNotification(UUID entityId, String title, String message, String notificationType,
			String priority) {
		EntityNotification notification = new EntityNotification();
		notification.setTitle(title);
		notification.setMessage(message);
		notification.setNotificationType(notificationType);
		notification.setPriority(priority != null ? priority : "NORMAL");
		notification.setCreatedAt(LocalDateTime.now());
		notification.setActive(true);

		// Note: entity and recipients will be set when sending
		return notificationRepository.save(notification);
	}

	public void sendNotificationToEntitySubscribers(UUID entityId, String title, String message,
			String notificationType, String priority) {
		// Get all active subscribers of the entity
		List<EntitySubscription> subscriptions = subscriptionRepository.findActiveSubscriptionsByEntity(entityId);

//        if (subscriptions.isEmpty()) {
//            throw new RuntimeException("No active subscribers found for entity: " + entityId);
//        }

//        // Create notification
//        EntityNotification notification = createNotification(entityId, title, message, notificationType, priority);
//
//        // Get the entity
//        Entity_Item entity = subscriptions.get(0).getEntity();
//
//        // Set entity and recipients
//        notification.setEntity(entity);
//
//        // Add all subscribers as recipients
//        for (EntitySubscription subscription : subscriptions) {
//            notification.getRecipients().add(subscription.getUser());
//        }
//
//        notificationRepository.save(notification);
//
//        // Send real-time notification via WebSocket
//        webSocketService.sendNotificationToEntitySubscribers(entityId, title, message, notificationType, priority,
		// entity.getName());
	}

	public void sendSMSAndEmailToSubscribers(UUID entityId, String title, String message, String notificationType,
			String priority) {
		// Get all active subscribers of the entity
		List<EntitySubscription> subscriptions = subscriptionRepository.findActiveSubscriptionsByEntity(entityId);

		if (subscriptions.isEmpty()) {
			System.out.println("No active subscribers found for entity: " + entityId);
			return;
		}

		// Get the entity
		Entity_Item entity = subscriptions.get(0).getEntity();

		// Create and save notification
//		EntityNotification notification = createNotification(entityId, title, message, notificationType, priority);
//		notification.setEntity(entity);

		// Add all subscribers as recipients
//		for (EntitySubscription subscription : subscriptions) {
//			notification.getRecipients().add(subscription.getUser());
//		}
//
//		notificationRepository.save(notification);

//		// Send real-time notification via WebSocket
//		webSocketService.sendNotificationToEntitySubscribers(entityId, title, message, notificationType, priority,
//				entity.getName());

		// Send emails and SMS to all subscribers
		sendBulkNotifications(subscriptions,entity,title,message,notificationType,priority);
	}

	private void sendBulkNotifications(List<EntitySubscription> subscriptions, Entity_Item entity, String title,
			String message, String notificationType, String priority) {

		// Send welcome notifications for new subscribers
		//sendWelcomeNotifications(subscriptions, entity);

		// Send bulk emails only (SMS disabled for now)
		try {
			emailService.sendBulkEmailToSubscribers(
				subscriptions.stream().map(EntitySubscription::getUser).toList(),
				entity, title, message, notificationType);
		} catch (MessagingException e) {
			System.err.println("Failed to send bulk emails: " + e.getMessage());
		}

		// SMS functionality disabled as requested
		// smsService.sendBulkSMSToSubscribers(...) - will be enabled in future
	}

	private void sendWelcomeNotifications(List<EntitySubscription> subscriptions, Entity_Item entity) {
		LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24); // Consider new subscribers within 24 hours

		for (EntitySubscription subscription : subscriptions) {
			if (subscription.getSubscribedAt().isAfter(cutoffTime)) {
				try {
					// Send welcome email
					emailService.sendWelcomeEmail(subscription.getUser(), entity);

					// Send welcome SMS (disabled for now)
					// smsService.sendWelcomeSMS(subscription.getUser(), entity);

				} catch (MessagingException e) {
					System.err.println("Failed to send welcome notification to " +
						subscription.getUser().getEmail() + ": " + e.getMessage());
				}
			}
		}
	}

	public void sendNotificationToUser(UUID entityId, UUID userId, String title, String message,
			String notificationType, String priority) {
		// Verify user is subscribed to the entity
		if (!subscriptionService.isUserSubscribedToEntity(userId, entityId)) {
			throw new RuntimeException("User is not subscribed to this entity");
		}

		// Create notification
		EntityNotification notification = createNotification(entityId, title, message, notificationType, priority);

		// Set entity and recipient
		Entity_Item entity = subscriptionService.getEntitySubscribers(entityId).stream().map(sub -> sub.getEntity())
				.findFirst().orElseThrow(() -> new RuntimeException("Entity not found"));

		notification.setEntity(entity);
		notification.getRecipients()
				.add(subscriptionService.getUserSubscriptions(userId).stream().map(EntitySubscription::getUser)
						.findFirst().orElseThrow(() -> new RuntimeException("User not found")));

		notificationRepository.save(notification);

		// Send real-time notification via WebSocket
		webSocketService.sendNotificationToUser(userId.toString(), title, message, entityId, notificationType, priority,
				entity.getName());
	}

	public List<EntityNotification> getEntityNotifications(UUID entityId) {
		return notificationRepository.findActiveByEntityOrderByCreatedAtDesc(entityId);
	}

	public List<EntityNotification> getUserNotifications(UUID userId) {
		return notificationRepository.findNotificationsForUser(userId);
	}

	public List<EntityNotification> getRecentNotifications(LocalDateTime fromDate) {
		return notificationRepository.findRecentNotifications(fromDate);
	}

	public EntityNotification scheduleNotification(UUID entityId, String title, String message, String notificationType,
			String priority, LocalDateTime scheduledFor) {
		EntityNotification notification = createNotification(entityId, title, message, notificationType, priority);
		notification.setScheduledFor(scheduledFor);

		return notificationRepository.save(notification);
	}

	public void markNotificationAsInactive(UUID notificationId) {
		EntityNotification notification = notificationRepository.findById(notificationId)
				.orElseThrow(() -> new RuntimeException("Notification not found"));

		notification.setActive(false);
		notificationRepository.save(notification);
	}
}
