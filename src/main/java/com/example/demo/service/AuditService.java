package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.security.CustomUserDetails;

@Service
public class AuditService {

	@Autowired
	private UserService userService;

	/**
	 * Lightweight helper: get current username/email from SecurityContext without DB access.
	 * Returns null if anonymous or unavailable.
	 */
	public String getCurrentUserEmailNoDb() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			return null;
		}
		if (principal instanceof CustomUserDetails) {
			return ((CustomUserDetails) principal).getUsername();
		}
		if (principal instanceof String) {
			String name = (String) principal;
			if (!"anonymousUser".equals(name)) {
				return name;
			}
		}
		return null;
	}

	/**
	 * Get current authenticated user for audit purposes Returns null if no user is
	 * authenticated (anonymous operations)
	 */
	public User getCurrentUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null && authentication.isAuthenticated()
					&& !authentication.getPrincipal().equals("anonymousUser")) {

				// This assumes your UserDetails implements or extends User entity
				// You may need to adjust based on your authentication setup
				Object principal = authentication.getPrincipal();
				if (principal instanceof CustomUserDetails) {
					CustomUserDetails customUserDetails = (CustomUserDetails) principal;
					if (customUserDetails == null)
						return null;
					User user = userService.getUserByName(customUserDetails.getUsername());
					return user;
				}

				// If using custom UserDetails, convert to User entity
				// return userService.findByUsername(authentication.getName());
			}

			return null; // Anonymous user
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Get system user for automated operations You can create a special system user
	 * in your database
	 */
	public User getSystemUser() {
		// return userService.findByUsername("SYSTEM");
		return null; // For now, return null
	}
}
