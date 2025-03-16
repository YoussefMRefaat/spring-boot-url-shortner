package com.youssef.urlshortener.service;

import com.youssef.urlshortener.entity.User;
import com.youssef.urlshortener.exception.InvalidTokenException;
import com.youssef.urlshortener.repository.UserRepository;
import com.youssef.urlshortener.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public User getUserFromToken(String token) {
        String email = jwtUtil.extractEmail(token.replace("Bearer ", ""));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidTokenException("Invalid or expired token"));
    }
}