package com.codeatlas.auth.exception;

public class InvalidOtpException extends RuntimeException {

    public InvalidOtpException() {
        super("Invalid OTP");
    }
}
