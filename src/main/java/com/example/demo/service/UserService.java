package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.UserCountResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(UUID id, User userDetails) {
		User user = getUserById(id);

		// Update fields as needed based on your User entity
		if (userDetails.getName() != null) {
			user.setName(userDetails.getName());
		}
		if (userDetails.getEmail() != null) {
			user.setEmail(userDetails.getEmail());
		}
		if (userDetails.getPhone() != null) {
			user.setPhone(userDetails.getPhone());
		}
		if (userDetails.getPasswordHash() != null) {
			user.setPasswordHash(userDetails.getPasswordHash());
		}
		if (userDetails.getRole() != null) {
			user.setRole(userDetails.getRole());
		}
		if (userDetails.getVillage() != null) {
			user.setVillage(userDetails.getVillage());
		}
		user.setActive(userDetails.isActive());

		return userRepository.save(user);
	}

	public UserCountResponse getCount() {
		return userRepository.getUserCount();
	}

	public List<User> getUserByRole(String roleString) {
		 Role role = Role.valueOf(roleString);

		return userRepository.findByRole(role);
	}

	public List<User> getUsersAndRoleByVillage(UUID villageId, String roleName) {
		Role role = Role.valueOf(roleName);
		return userRepository.findByVillageIdAndRole(villageId, role);
	}

	public List<User> getUsersByVillage(UUID villageId) {
		
		return userRepository.findByVillageId(villageId);
	}

	public boolean isUserActive(UUID userId) {
		return userRepository.findByIdAndIsActiveTrue(userId).isPresent();
	}
}
