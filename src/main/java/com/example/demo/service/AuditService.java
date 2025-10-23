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
	 * Get current authenticated user for audit purposes Returns null if no user is
	 * authenticated (anonymous operations)
	 */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
				&& !authentication.getPrincipal().equals("anonymousUser")) {

			// This assumes your UserDetails implements or extends User entity
			// You may need to adjust based on your authentication setup
			Object principal = authentication.getPrincipal();
			if (principal instanceof CustomUserDetails) {
				CustomUserDetails customUserDetails = (CustomUserDetails) principal;
				if(customUserDetails==null)
					return null;
				User user=userService.getUserByName(customUserDetails.getUsername());
				return user;
			}

			// If using custom UserDetails, convert to User entity
			// return userService.findByUsername(authentication.getName());
		}

		return null; // Anonymous user
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
