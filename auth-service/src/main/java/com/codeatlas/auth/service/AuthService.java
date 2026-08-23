package com.codeatlas.auth.service;

import com.codeatlas.auth.dto.RegisterRequest;
import com.codeatlas.auth.exception.DuplicateEmailException;
import com.codeatlas.auth.repository.UserRepository;
import com.codeatlas.auth.utils.OtpGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthService {
    private final OtpGenerator otpGenerator;
    private final PendingRegistrationService pendingRegistrationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(OtpGenerator otpGenerator, PendingRegistrationService pendingRegistrationService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.otpGenerator = otpGenerator;
        this.pendingRegistrationService = pendingRegistrationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new DuplicateEmailException("Email is already registered");
        }
        String otp = otpGenerator.generate();
        PendingRegistration registration = new PendingRegistration();
        registration.setEmail(normalizedEmail);
        registration.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        registration.setOtpHash(passwordEncoder.encode(otp));
        pendingRegistrationService.save(registration);
    }
}
