package com.codeatlas.auth.controller;

import com.codeatlas.auth.dto.LoginRequest;
import com.codeatlas.auth.dto.LoginResponse;
import com.codeatlas.auth.dto.RegisterRequest;
import com.codeatlas.auth.dto.VerifyRegistrationRequest;
import com.codeatlas.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/register/verify")
    public ResponseEntity<Void> verify(@Valid @RequestBody VerifyRegistrationRequest request) {
        authService.verify(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        System.out.println(">>> LOGIN CONTROLLER REACHED");
        return ResponseEntity.ok(authService.login(request));
    }
}