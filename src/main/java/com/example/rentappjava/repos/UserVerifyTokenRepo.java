package com.example.rentappjava.repos;

import com.example.rentappjava.models.UserVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerifyTokenRepo extends JpaRepository<UserVerifyToken, Long> {
    Optional<UserVerifyToken> findUserVerifyTokenByToken(String token);
}
