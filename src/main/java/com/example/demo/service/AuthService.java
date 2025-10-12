package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthResponse register(RegisterRequest request) {
		Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException("User with this email already exists");
		}
		

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		userRepository.save(user);

		String accessToken = jwtService.generateToken(user);
		String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

		return new AuthResponse(accessToken, refreshToken);
	}

	public AuthResponse login(LoginRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			throw new BadCredentialsException("Invalid credentials");
		}

		String accessToken = jwtService.generateToken(user);
		String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

		return new AuthResponse(accessToken, refreshToken);
	}
}
