package com.example.rentappjava.services;

import com.example.rentappjava.models.RefreshToken;
import com.example.rentappjava.repos.RefreshTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepo.save(refreshToken);
    }

    boolean validateRefreshToken(String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepo.findByToken(token);
        return optionalRefreshToken.isPresent();
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepo.deleteByToken(token);
    }
}