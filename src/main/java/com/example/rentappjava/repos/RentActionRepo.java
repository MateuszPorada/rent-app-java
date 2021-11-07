package com.example.rentappjava.repos;

import com.example.rentappjava.entities.RentAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentActionRepo extends JpaRepository<RentAction, Long> {
}
