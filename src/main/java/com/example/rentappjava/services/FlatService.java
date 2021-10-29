package com.example.rentappjava.services;

import com.example.rentappjava.dtos.FlatDTO;
import com.example.rentappjava.entities.Flat;
import com.example.rentappjava.repos.FlatRepo;
import com.example.rentappjava.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class FlatService {

    private final FlatRepo flatRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    public FlatDTO save(FlatDTO flatDTO) {
        Flat flat = modelMapper.map(flatDTO, Flat.class);
        flat.setOwner(authService.getCurrentUser());
        return modelMapper.map(flatRepo.save(flat), FlatDTO.class);
    }

    public FlatDTO getFlat(Long id) {
        return modelMapper.map(flatRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found")), FlatDTO.class);
    }

    public void deleteFlat(Long id) {
        flatRepo.delete(flatRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found")));
    }

    public List<FlatDTO> getFlatsByOwnerID(Long id) {
        List<Flat> flatList = flatRepo.findFlatByOwner(userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")));
        return flatList.stream().map(flat -> modelMapper.map(flat, FlatDTO.class)).collect(Collectors.toList());
    }
}
