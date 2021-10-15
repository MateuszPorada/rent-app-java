package com.example.rentappjava.controllers;

import com.example.rentappjava.models.Room;
import com.example.rentappjava.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private RoomService roomService;

    @GetMapping("/{id}")
    public ResponseEntity<Room> getFlat(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);
    }
}
