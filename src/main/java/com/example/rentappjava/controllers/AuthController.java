package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.RegisterRequestDTO;
import com.example.rentappjava.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        authService.signUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        authService.verify(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
