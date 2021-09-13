package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.FlatDTO;
import com.example.rentappjava.models.Flat;
import com.example.rentappjava.services.FlatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/flat")
public class FlatController {

    private final FlatService flatService;

    @PostMapping
    public ResponseEntity<Flat> createFlat(@RequestBody FlatDTO flatDTO) {
        return new ResponseEntity<>(flatService.save(flatDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flat> getFlat(@PathVariable Long id){
        return new ResponseEntity<>(flatService.getFlat(id), HttpStatus.OK);
    }
}

