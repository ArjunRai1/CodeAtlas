package com.codeatlas.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class PendingRegistrationService {
    @Value("${codeatlas.auth.pending-registration.ttl}")
    private Duration ttl;

    private final RedisTemplate<String, PendingRegistration> redisTemplate;

    public PendingRegistrationService(RedisTemplate<String, PendingRegistration> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(String prefix, String identifier){
        return prefix + ":" + identifier;
    }

    public void save(PendingRegistration registration){
        String key = getKey("registration", registration.getEmail());
        redisTemplate.opsForValue().set(key, registration, ttl);
    }

    public Optional<PendingRegistration> getEmail(String email) {
        String key = getKey("registration", email);
        PendingRegistration registration = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(registration);
    }

    public void delete(String email) {
        String key = getKey("registration", email);
        redisTemplate.delete(key);
    }
}
