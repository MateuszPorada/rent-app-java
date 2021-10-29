package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.FlatDTO;
import com.example.rentappjava.services.FlatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/flats")
public class FlatController {

    private final FlatService flatService;

    @PostMapping
    public ResponseEntity<FlatDTO> createFlat(@RequestBody FlatDTO flatDTO) {
        return new ResponseEntity<>(flatService.save(flatDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlatDTO> getFlat(@PathVariable Long id) {
        return new ResponseEntity<>(flatService.getFlat(id), HttpStatus.OK);
    }

    @GetMapping("/by-owner/{id}")
    public ResponseEntity<List<FlatDTO>> getFlatsByOwnerID(@PathVariable Long id) {
        return new ResponseEntity<List<FlatDTO>>(flatService.getFlatsByOwnerID(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFlat(@PathVariable Long id) {
        flatService.deleteFlat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

