package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User extends BaseEntity {

	private String name;
	private String email;
	private String phone;
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne
	private Village village;

	// Entity subscriptions - users can subscribe to multiple entities
	@OneToMany(mappedBy = "user")
	@JsonManagedReference(value = "user-subscriptions")
	private List<EntitySubscription> entitySubscriptions = new ArrayList<>();
}
