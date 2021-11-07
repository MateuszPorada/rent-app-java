package com.example.rentappjava.services;

import com.example.rentappjava.repos.RentActionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentActionService {
    private final RentActionRepo rentActionRepo;
}
