package com.example.rentappjava.repos;

import com.example.rentappjava.models.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long> {
}
