package com.example.rentappjava.repos;

import com.example.rentappjava.entities.Flat;
import com.example.rentappjava.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long> {
    List<Flat> findFlatByOwner(User user);
}
