package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.response.UserCountResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable UUID id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.createUser(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
		return ResponseEntity.ok(userService.updateUser(id, user));
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
//		userService.deleteUser(id);
//		return ResponseEntity.ok().build();
//	}

	@GetMapping("/count")
	public ResponseEntity<UserCountResponse> getUserCount() {
		return ResponseEntity.ok(userService.getCount());
	}

	@GetMapping("/role/{role}")
	public ResponseEntity<List<User>> getUserByRole(@PathVariable String role) {
		return ResponseEntity.ok(userService.getUserByRole(role));
	}

	@GetMapping("/all/roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		return ResponseEntity.ok(userService.getAllRoles());
	}

	@GetMapping("/village/{villageId}/{roleName}")
	public ResponseEntity<List<User>> getUsersAndRoleByVillage(@PathVariable UUID villageId,
			@PathVariable String roleName) {
		return ResponseEntity.ok(userService.getUsersAndRoleByVillage(villageId, roleName));
	}

	@GetMapping("/village/{villageId}")
	public ResponseEntity<List<User>> getUsersByVillage(@PathVariable UUID villageId) {
		return ResponseEntity.ok(userService.getUsersByVillage(villageId));
	}
}
