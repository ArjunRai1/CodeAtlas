package com.codeatlas.auth.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PendingRegistration {
    private String email;
    private String password;
    private String otpHash;
}
