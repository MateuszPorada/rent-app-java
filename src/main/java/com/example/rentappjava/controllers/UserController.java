package com.example.rentappjava.controllers;

import com.example.rentappjava.dtos.UserDTO;
import com.example.rentappjava.models.User;
import com.example.rentappjava.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getUsers() {
        return userService.findUsers();
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }
}
