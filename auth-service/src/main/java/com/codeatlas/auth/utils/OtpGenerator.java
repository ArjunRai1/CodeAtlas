package com.codeatlas.auth.utils;

import java.security.SecureRandom;

public class OtpGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(){
        int randomOTP = RANDOM.nextInt(1_000_000);
        String otp = String.format("%06d", randomOTP);
        return otp;
    }
}
