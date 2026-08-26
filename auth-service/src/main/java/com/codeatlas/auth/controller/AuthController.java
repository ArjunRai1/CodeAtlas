package com.codeatlas.auth.controller;

import com.codeatlas.auth.dto.LoginRequest;
import com.codeatlas.auth.dto.LoginResponse;
import com.codeatlas.auth.dto.RegisterRequest;
import com.codeatlas.auth.dto.VerifyRegistrationRequest;
import com.codeatlas.auth.service.AuthService;
import com.codeatlas.auth.service.GithubOAuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final GithubOAuthService githubOAuthService;

    public AuthController(AuthService authService, GithubOAuthService githubOAuthService) {
        this.authService = authService;
        this.githubOAuthService = githubOAuthService;
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

    @GetMapping("/github")
    public ResponseEntity<Void> githubLogin(HttpSession session) {
        String authorizationUrl = githubOAuthService.getAuthorizationUrl(session);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(authorizationUrl)).build();
    }

    @GetMapping("/github/callback")
    public ResponseEntity<String> githubCallback(@RequestParam String code, @RequestParam String state, HttpSession session) {
        String expectedState = (String) session.getAttribute("github_oauth_state");
        if (expectedState == null || !expectedState.equals(state)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OAuth state");
        }
        session.removeAttribute("github_oauth_state");
        String githubAccessToken = githubOAuthService.exchangeCode(code);
        return ResponseEntity.ok("GitHub authorization successful");
    }
}