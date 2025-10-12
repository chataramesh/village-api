package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RefreshRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.JwtService;
import com.example.demo.service.RefreshTokenService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

	
    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request) {
        RefreshToken token = refreshTokenRepo.findByToken(request.getRefreshToken())
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (!refreshTokenService.isValid(token)) {
        	refreshTokenRepo.delete(token);
            throw new RuntimeException("Expired refresh token");
        }

        String newAccessToken = jwtService.generateToken(token.getUser());
        String newRefreshToken = refreshTokenService.createRefreshToken(token.getUser()).getToken();

        return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
    }
}
