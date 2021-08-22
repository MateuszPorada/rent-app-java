package com.example.rentappjava.repos;

import com.example.rentappjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
