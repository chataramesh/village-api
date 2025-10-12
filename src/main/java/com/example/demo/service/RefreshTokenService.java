package com.example.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	@Value("${jwt.refresh-expiration}")
	private long refreshExpirationMs;

	@Autowired
	private RefreshTokenRepository refreshTokenRepo;

	public RefreshToken createRefreshToken(User user) {
		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());

		LocalDateTime expiryTime = LocalDateTime.now().plus(Duration.ofMillis(refreshExpirationMs));
		token.setExpiry(expiryTime);

		return refreshTokenRepo.save(token);
	}

	public boolean isValid(RefreshToken token) {
		return token.getExpiry().isAfter(LocalDateTime.now());
	}

	public void deleteByUser(User user) {
		refreshTokenRepo.deleteByUser(user);
	}
}
