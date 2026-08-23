package com.codeatlas.auth.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationOtp(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("CodeAtlas - Email Verification");
        message.setText("Your CodeAtlas verification code is: " + otp + "\n\nThis code will expire in 10 minutes.");
        mailSender.send(message);
    }
}
