package com.example.rentappjava.repos;

import com.example.rentappjava.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepo extends JpaRepository<Room, Long> {
}
