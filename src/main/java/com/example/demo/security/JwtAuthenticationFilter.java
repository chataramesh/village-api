package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;  // your service to parse/validate JWT
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        System.out.println("JwtAuthenticationFilter - Request URI: " + request.getRequestURI());
        System.out.println("JwtAuthenticationFilter - Auth Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JwtAuthenticationFilter - No valid Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        System.out.println("JwtAuthenticationFilter - JWT Token: " + jwt);
        
        try {
            userEmail = jwtService.extractUsername(jwt);
            System.out.println("JwtAuthenticationFilter - Extracted email: " + userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("JwtAuthenticationFilter - Loaded user: " + userDetails.getUsername());
                System.out.println("JwtAuthenticationFilter - User authorities: " + userDetails.getAuthorities());

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("JwtAuthenticationFilter - Authentication set successfully");
                    System.out.println("JwtAuthenticationFilter - Authentication object: " + SecurityContextHolder.getContext().getAuthentication());
                    System.out.println("JwtAuthenticationFilter - Is authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                } else {
                    System.out.println("JwtAuthenticationFilter - Token validation failed");
                }
            }
        } catch (Exception e) {
            System.out.println("JwtAuthenticationFilter - Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("JwtAuthenticationFilter - About to continue filter chain");
        System.out.println("JwtAuthenticationFilter - Current authentication: " + SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request, response);
        System.out.println("JwtAuthenticationFilter - After filter chain, response status: " + response.getStatus());
    }
}
