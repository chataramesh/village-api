package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EntityNotification;
import com.example.demo.entity.EntitySubscription;
import com.example.demo.entity.Entity_Item;
import com.example.demo.enums.EntityStatus;
import com.example.demo.repository.EntityItemRepository;
import com.example.demo.service.EntityItemService;
import com.example.demo.service.EntityNotificationService;
import com.example.demo.service.EntitySubscriptionService;

@RestController
@RequestMapping("/api/entities")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EntityController {

	@Autowired
	private EntitySubscriptionService subscriptionService;

	@Autowired
	private EntityNotificationService notificationService;

	@Autowired
	private EntityItemRepository entityRepository;

	@Autowired
	private EntityItemService entityItemService;

	// ==================== BASIC CRUD OPERATIONS ====================

	@GetMapping("/all")
	public ResponseEntity<List<Entity_Item>> getAllEntities() {
		List<Entity_Item> responses = entityRepository.findAll();
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Entity_Item> getEntityById(@PathVariable UUID id) {
		Entity_Item entity = entityRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
		return ResponseEntity.ok(entity);
	}

	@PostMapping("/create")
	public ResponseEntity<Entity_Item> createEntity(@RequestBody Entity_Item entity) {
		Entity_Item savedEntity = entityRepository.save(entity);
		return ResponseEntity.ok(savedEntity);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Entity_Item> updateEntity(@PathVariable UUID id, @RequestBody Entity_Item entityDetails) {
		Entity_Item entity = entityRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));

//		System.out.println("entity  " + entity.getOwner() + "  " + entity.getUpdatedBy());
//		System.out
//				.println("entityDetails  " + entityDetails.getOwner().getName() + "  " + entityDetails.getCreatedBy());
		// Update fields
		if (entityDetails.getName() != null) {
			entity.setName(entityDetails.getName());
		}
		if (entityDetails.getDescription() != null) {
			entity.setDescription(entityDetails.getDescription());
		}
		if (entityDetails.getType() != null) {
			entity.setType(entityDetails.getType());
		}
		if (entityDetails.getOpeningTime() != null) {
			entity.setOpeningTime(entityDetails.getOpeningTime());
		}
		if (entityDetails.getClosingTime() != null) {
			entity.setClosingTime(entityDetails.getClosingTime());
		}
		if (entityDetails.getStatus() != null) {
			entity.setStatus(entityDetails.getStatus());
		}
		entity.setActive(entityDetails.isActive());

		if (entityDetails.getLatitude() != null) {
			entity.setLatitude(entityDetails.getLatitude());
		}
		if (entityDetails.getLongitude() != null) {
			entity.setLongitude(entityDetails.getLongitude());
		}
		if (entityDetails.getAddress() != null) {
			entity.setAddress(entityDetails.getAddress());
		}
		if (entityDetails.getContactNumber() != null) {
			entity.setContactNumber(entityDetails.getContactNumber());
		}
		if (entityDetails.getEmail() != null) {
			entity.setEmail(entityDetails.getEmail());
		}
		if (entityDetails.getCapacity() != null) {
			entity.setCapacity(entityDetails.getCapacity());
		}
		if (entityDetails.getAvailableSlots() != null) {
			entity.setAvailableSlots(entityDetails.getAvailableSlots());
		}
		if (entityDetails.getOwner() != null) {
			entity.setOwner(entityDetails.getOwner());
		}

		Entity_Item updatedEntity = entityRepository.save(entity);

		return ResponseEntity.ok(updatedEntity);
	}

	@PutMapping("/{entityId}/{ownerId}")
	public ResponseEntity<Entity_Item> updateEntityByOwner(@PathVariable UUID entityId, @PathVariable UUID ownerId,
			@RequestBody Entity_Item entityDetails) {

		try {
			Entity_Item entity = entityRepository.findById(entityId)
					.orElseThrow(() -> new RuntimeException("Entity not found with id: " + entityId));

			// Update fields
			if (entityDetails.getName() != null) {
				entity.setName(entityDetails.getName());
			}
			if (entityDetails.getDescription() != null) {
				entity.setDescription(entityDetails.getDescription());
			}
			if (entityDetails.getType() != null) {
				entity.setType(entityDetails.getType());
			}
			if (entityDetails.getOpeningTime() != null) {
				entity.setOpeningTime(entityDetails.getOpeningTime());
			}
			if (entityDetails.getClosingTime() != null) {
				entity.setClosingTime(entityDetails.getClosingTime());
			}
			if (entityDetails.getStatus() != null) {
				entity.setStatus(entityDetails.getStatus());
			}
			entity.setActive(entityDetails.isActive());

			if (entityDetails.getLatitude() != null) {
				entity.setLatitude(entityDetails.getLatitude());
			}
			if (entityDetails.getLongitude() != null) {
				entity.setLongitude(entityDetails.getLongitude());
			}
			if (entityDetails.getAddress() != null) {
				entity.setAddress(entityDetails.getAddress());
			}
			if (entityDetails.getContactNumber() != null) {
				entity.setContactNumber(entityDetails.getContactNumber());
			}
			if (entityDetails.getEmail() != null) {
				entity.setEmail(entityDetails.getEmail());
			}
			if (entityDetails.getCapacity() != null) {
				entity.setCapacity(entityDetails.getCapacity());
			}
			if (entityDetails.getAvailableSlots() != null) {
				entity.setAvailableSlots(entityDetails.getAvailableSlots());
			}
			if (entityDetails.getOwner() != null) {
				entity.setOwner(entityDetails.getOwner());
			}

			Entity_Item updatedEntity = entityRepository.save(entity);
			
			// Temporarily disable notification sending to isolate the issue
			String updateMessage = String.format(
					"Entity '%s' has been updated. Check the latest information for any changes.", updatedEntity.getName());
//			
			 notificationService.sendSMSAndEmailToSubscribers(updatedEntity.getId(), "Entity Updated", updateMessage, "UPDATE", "NORMAL");
			return ResponseEntity.ok(updatedEntity);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error updating entity by owner: " + e.getLocalizedMessage());
			return ResponseEntity.ok(null);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEntity(@PathVariable UUID id) {
		Entity_Item entity = entityRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Entity not found with id: " + id));
		entityRepository.delete(entity);
		return ResponseEntity.ok("Entity deleted successfully");
	}

	// ==================== FILTERING ENDPOINTS ====================

	@GetMapping("/village/{villageId}")
	public ResponseEntity<List<Entity_Item>> getEntitiesByVillage(@PathVariable UUID villageId) {
		List<Entity_Item> entities = entityRepository.findByOwnerVillageId(villageId);
		return ResponseEntity.ok(entities);
	}

	@GetMapping("/village/{villageId}/active")
	public ResponseEntity<List<Entity_Item>> getActiveEntitiesByVillage(@PathVariable UUID villageId) {
		List<Entity_Item> entities = entityRepository.findByOwnerVillageIdAndIsActiveTrue(villageId);
		return ResponseEntity.ok(entities);
	}

	@GetMapping("/type/{type}")
	public ResponseEntity<List<Entity_Item>> getEntitiesByType(@PathVariable String type) {
		List<Entity_Item> entities = entityRepository.findByType(type);
		return ResponseEntity.ok(entities);
	}

	@GetMapping("/status/{status}")
	public ResponseEntity<List<Entity_Item>> getEntitiesByStatus(
			@PathVariable EntityStatus status) {
		List<Entity_Item> entities = entityRepository.findByStatusAndActive(status);
		return ResponseEntity.ok(entities);
	}

	@GetMapping("/owner/{ownerId}")
	public ResponseEntity<List<Entity_Item>> getEntitiesByOwner(@PathVariable UUID ownerId) {
		List<Entity_Item> entities = entityRepository.findByOwnerId(ownerId);
		return ResponseEntity.ok(entities);
	}

	// ==================== SUBSCRIPTION MANAGEMENT ====================

	@PostMapping("/{entityId}/subscribe/{userId}")
	public ResponseEntity<EntitySubscription> subscribeToEntity(@PathVariable UUID entityId, @PathVariable UUID userId,
			@RequestParam(defaultValue = "GENERAL") String subscriptionType) {
		EntitySubscription subscription = subscriptionService.subscribeToEntity(userId, entityId, subscriptionType);
		return ResponseEntity.ok(subscription);
	}

	@DeleteMapping("/{entityId}/unsubscribe/{userId}")
	public ResponseEntity<String> unsubscribeFromEntity(@PathVariable UUID entityId, @PathVariable UUID userId) {
		subscriptionService.unsubscribeFromEntity(userId, entityId);
		return ResponseEntity.ok("Successfully unsubscribed from entity");
	}

	@GetMapping("/subscriptions/user/{userId}")
	public ResponseEntity<List<EntitySubscription>> getUserSubscriptions(@PathVariable UUID userId) {
		List<EntitySubscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
		return ResponseEntity.ok(subscriptions);
	}

	@GetMapping("/subscriptions/entity/{entityId}")
	public ResponseEntity<List<EntitySubscription>> getEntitySubscribers(@PathVariable UUID entityId) {
		List<EntitySubscription> subscribers = subscriptionService.getEntitySubscribers(entityId);
		return ResponseEntity.ok(subscribers);
	}

	@GetMapping("/subscribed-entities/{userId}")
	public ResponseEntity<List<Entity_Item>> getSubscribedEntities(@PathVariable UUID userId) {
		List<Entity_Item> entities = subscriptionService.getSubscribedEntities(userId);
		return ResponseEntity.ok(entities);
	}

	@GetMapping("/{entityId}/is-subscribed/{userId}")
	public ResponseEntity<Boolean> isUserSubscribedToEntity(@PathVariable UUID entityId, @PathVariable UUID userId) {
		boolean isSubscribed = subscriptionService.isUserSubscribedToEntity(userId, entityId);
		return ResponseEntity.ok(isSubscribed);
	}

	// ==================== NOTIFICATION MANAGEMENT ====================

	@PostMapping("/{entityId}/notifications")
	public ResponseEntity<EntityNotification> sendNotificationToEntity(@PathVariable UUID entityId,
			@RequestParam String title, @RequestParam String message,
			@RequestParam(defaultValue = "GENERAL") String notificationType,
			@RequestParam(defaultValue = "NORMAL") String priority) {
		notificationService.sendNotificationToEntitySubscribers(entityId, title, message, notificationType, priority);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{entityId}/notifications")
	public ResponseEntity<List<EntityNotification>> getEntityNotifications(@PathVariable UUID entityId) {
		List<EntityNotification> notifications = notificationService.getEntityNotifications(entityId);
		return ResponseEntity.ok(notifications);
	}

	@GetMapping("/notifications/user/{userId}")
	public ResponseEntity<List<EntityNotification>> getUserNotifications(@PathVariable UUID userId) {
		List<EntityNotification> notifications = notificationService.getUserNotifications(userId);
		return ResponseEntity.ok(notifications);
	}
}
