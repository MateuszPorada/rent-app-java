package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.RentActionDTO;
import com.example.rentappjava.services.RentActionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rent")
public class RentActionController {
    private final RentActionService rentActionService;

    @PostMapping
    private ResponseEntity<RentActionDTO> createRentAction(@RequestBody RentActionDTO rentActionDTO) {
        return new ResponseEntity<>(rentActionService.createRentAction(rentActionDTO), HttpStatus.CREATED);
    }

    @GetMapping("/by-space/{id}")
    private ResponseEntity<List<RentActionDTO>> getRentActionBySpace(@PathVariable Long id) {
        return new ResponseEntity<>(rentActionService.getRentActionBySpaceID(id), HttpStatus.OK);
    }

    @GetMapping("/by-tenant/{id}")
    private ResponseEntity<List<RentActionDTO>> getRentActionByTenant(@PathVariable Long id) {
        return new ResponseEntity<>(rentActionService.getRentActionByTenantID(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentActionDTO> updateRentAction(@RequestBody RentActionDTO rentActionDTO, @PathVariable Long id) {
        return new ResponseEntity<>(rentActionService.update(rentActionDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteRentActionById(@PathVariable Long id) {
        rentActionService.deleteActionById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
