package com.example.rentappjava.services;

import com.example.rentappjava.models.Room;
import com.example.rentappjava.repos.RoomRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepo roomRepo;

    public Room getRoomById(Long id) {
        return roomRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
