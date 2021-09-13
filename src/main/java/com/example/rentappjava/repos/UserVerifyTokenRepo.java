package com.example.rentappjava.repos;

import com.example.rentappjava.models.UserVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserVerifyTokenRepo extends JpaRepository<UserVerifyToken, Long> {
    Optional<UserVerifyToken> findUserVerifyTokenByToken(String token);
}
