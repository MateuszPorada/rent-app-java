package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.RentActionDTO;
import com.example.rentappjava.services.RentActionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/rent")
public class RentActionController {
    private final RentActionService rentActionService;

    @PostMapping
    public ResponseEntity<RentActionDTO> addRentAction(@RequestBody RentActionDTO rentActionDTO) {
        return new ResponseEntity<>(rentActionService.addRentAction(rentActionDTO), HttpStatus.CREATED);
    }
}
