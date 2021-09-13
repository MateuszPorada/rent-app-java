package com.example.rentappjava.services;

import com.example.rentappjava.dtos.FlatDTO;
import com.example.rentappjava.models.Flat;
import com.example.rentappjava.repos.FlatRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class FlatService {

    private final FlatRepo flatRepo;
    private final ModelMapper modelMapper;

    public Flat save(FlatDTO flatDTO) {
        return flatRepo.save(modelMapper.map(flatDTO, Flat.class));
    }

    public Flat getFlat(Long id) {
        return flatRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
