package com.example.rentappjava.repos;

import com.example.rentappjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);
}
