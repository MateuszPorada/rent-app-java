package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.LoginRequest;
import com.example.rentappjava.dtos.LoginResponse;
import com.example.rentappjava.dtos.RefreshTokenRequest;
import com.example.rentappjava.dtos.RegisterRequestDTO;
import com.example.rentappjava.services.AuthService;
import com.example.rentappjava.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUser(@RequestBody RegisterRequestDTO registerRequestDTO) {
        authService.signUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        authService.verify(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("token/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return new ResponseEntity<>(authService.refreshToken(refreshTokenRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok().body("Token deleted succesfully");
    }

}
