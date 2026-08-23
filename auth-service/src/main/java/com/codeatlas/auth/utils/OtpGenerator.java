package com.codeatlas.auth.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OtpGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(){
        int randomOTP = RANDOM.nextInt(1_000_000);
        String otp = String.format("%06d", randomOTP);
        return otp;
    }
}
