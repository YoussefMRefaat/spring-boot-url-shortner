package com.youssef.urlshortener.service;

import com.youssef.urlshortener.dto.LoginRequest;
import com.youssef.urlshortener.dto.LoginResponse;
import com.youssef.urlshortener.dto.SignupRequest;
import com.youssef.urlshortener.dto.SignupResponse;
import com.youssef.urlshortener.entity.User;
import com.youssef.urlshortener.exception.ConflictException;
import com.youssef.urlshortener.exception.InvalidCredentials;
import com.youssef.urlshortener.repository.UserRepository;
import com.youssef.urlshortener.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public SignupResponse signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ConflictException("Email is already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return new SignupResponse(token);
    }

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOptional.get().getPassword())) {
            throw new InvalidCredentials("Credentials doesn't match our records");
        }

        String token = jwtUtil.generateToken(request.getEmail());
        return new LoginResponse(token);
    }
}
