package com.example.rentappjava.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LoginResponse {
    private String authToken;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
}
