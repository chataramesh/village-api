package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entity.BaseEntity;
import com.example.demo.entity.User;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Component
public class EntityAuditListener {

	private static AuditService auditService;

	@Autowired
	public void init(AuditService auditService) {
		EntityAuditListener.auditService = auditService;
	}

	@PrePersist
	public void setCreatedFields(Object target) {
		if (target instanceof BaseEntity baseEntity) {
			LocalDateTime now = LocalDateTime.now();
			baseEntity.setCreatedAt(now);
			baseEntity.setUpdatedAt(now);

			if (auditService != null) {
				String email = auditService.getCurrentUserEmailNoDb();
				if (email != null) {
					baseEntity.setCreatedBy(email);
					baseEntity.setUpdatedBy(email);
				}
			}

		}
	}

	@PreUpdate
	public void setUpdatedFields(Object target) {
		try {
			if (target instanceof BaseEntity baseEntity) {
				baseEntity.setUpdatedAt(LocalDateTime.now());

				if (auditService != null) {
					String email = auditService.getCurrentUserEmailNoDb();
					if (email == null) {
						User systemUser = auditService.getSystemUser();
						email = systemUser != null ? systemUser.getEmail() : null;
					}

					if (email != null) {
						baseEntity.setUpdatedBy(email);
					}
				}
			}
		} catch (Exception e) {
			// Handle exception if needed
			e.printStackTrace();
		}

	}
}
