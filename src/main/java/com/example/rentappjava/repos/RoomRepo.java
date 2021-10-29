package com.example.rentappjava.repos;

import com.example.rentappjava.entities.Flat;
import com.example.rentappjava.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findAllByFlat(Flat flat);
}
