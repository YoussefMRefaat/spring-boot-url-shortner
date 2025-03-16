package com.youssef.urlshortener.controller;

import com.youssef.urlshortener.dto.LoginRequest;
import com.youssef.urlshortener.dto.LoginResponse;
import com.youssef.urlshortener.dto.SignupRequest;
import com.youssef.urlshortener.dto.SignupResponse;
import com.youssef.urlshortener.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
