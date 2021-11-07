package com.example.rentappjava.repos;

import com.example.rentappjava.entities.User;
import com.example.rentappjava.entities.UserVerifyToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerifyTokenRepo extends JpaRepository<UserVerifyToken, Long> {
    Optional<UserVerifyToken> findUserVerifyTokenByToken(String token);

    Optional<UserVerifyToken> findUserVerifyTokenByUser(User user);
}
