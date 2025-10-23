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

			User currentUser = auditService.getCurrentUser();
			if (currentUser != null) {
				baseEntity.setCreatedBy(currentUser.getEmail());
				baseEntity.setUpdatedBy(currentUser.getEmail());
			}

		}
	}

	@PreUpdate
	public void setUpdatedFields(Object target) {

		if (target instanceof BaseEntity baseEntity) {
			baseEntity.setUpdatedAt(LocalDateTime.now());

			User currentUser = auditService.getCurrentUser();
			if (currentUser == null) {
				currentUser = auditService.getSystemUser();
			}

			baseEntity.setUpdatedBy(currentUser.getEmail());
		}
	}
}
