package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.RentedSpaceDTO;
import com.example.rentappjava.services.RentedSpaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/spaces")
public class RentedSpaceController {

    private final RentedSpaceService rentedSpaceService;

    @PostMapping
    public ResponseEntity<RentedSpaceDTO> createRentedSpace(@RequestBody RentedSpaceDTO rentedSpaceDTO) {
        return new ResponseEntity<>(rentedSpaceService.save(rentedSpaceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentedSpaceDTO> getRentedSpace(@PathVariable Long id) {
        return new ResponseEntity<>(rentedSpaceService.getRentedSpace(id), HttpStatus.OK);
    }

    @GetMapping("/by-owner/{id}")
    public ResponseEntity<List<RentedSpaceDTO>> getRentedSpacesByOwnerID(@PathVariable Long id) {
        return new ResponseEntity<>(rentedSpaceService.getRentedSpacesByOwnerID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentedSpaceDTO> updateRentedSpace(@RequestBody RentedSpaceDTO rentedSpaceDTO, @PathVariable Long id) {
        return new ResponseEntity<>(rentedSpaceService.update(rentedSpaceDTO, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRentedSpace(@PathVariable Long id) {
        rentedSpaceService.deleteRentedSpace(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}

