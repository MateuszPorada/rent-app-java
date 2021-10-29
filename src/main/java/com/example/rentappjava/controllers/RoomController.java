package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.RoomDTO;
import com.example.rentappjava.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        return new ResponseEntity<>(roomService.save(roomDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);
    }

    @GetMapping("/by-flat/{id}")
    public ResponseEntity<List<RoomDTO>> getRoomsByFlat(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.getRoomByFlatId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
