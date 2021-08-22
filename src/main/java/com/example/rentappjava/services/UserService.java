package com.example.rentappjava.services;

import com.example.rentappjava.dtos.UserDTO;
import com.example.rentappjava.models.User;
import com.example.rentappjava.repos.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public UserService(UserRepo userRepo, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<List<User>> findUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Optional<User>> findUser(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent())
            return new ResponseEntity<>(userOptional, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<User> saveUser(UserDTO userDTO) {
        return new ResponseEntity<>(userRepo.save(modelMapper.map(userDTO, User.class)), HttpStatus.OK);
    }
}
