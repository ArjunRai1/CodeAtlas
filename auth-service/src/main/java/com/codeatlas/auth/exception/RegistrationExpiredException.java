package com.codeatlas.auth.exception;

public class RegistrationExpiredException extends RuntimeException {

    public RegistrationExpiredException() {
        super("Registration has expired or does not exist");
    }
}
