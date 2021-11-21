package com.example.rentappjava.services;

import com.example.rentappjava.dtos.RentedSpaceDTO;
import com.example.rentappjava.entities.RentedSpace;
import com.example.rentappjava.repos.RentedSpaceRepo;
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
public class RentedSpaceService {

    private final RentedSpaceRepo rentedSpaceRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    public RentedSpaceDTO createRentedSpace(RentedSpaceDTO rentedSpaceDTO) {
        RentedSpace rentedSpace = modelMapper.map(rentedSpaceDTO, RentedSpace.class);
        rentedSpace.setOwner(authService.getCurrentUser());
        return modelMapper.map(rentedSpaceRepo.save(rentedSpace), RentedSpaceDTO.class);
    }

    public RentedSpaceDTO getRentedSpace(Long id) {
        return modelMapper.map(rentedSpaceRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found")), RentedSpaceDTO.class);
    }


    public List<RentedSpaceDTO> getRentedSpacesByOwnerID(Long id) {
        List<RentedSpace> rentedSpaceList = rentedSpaceRepo.findRentedSpaceByOwner(userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found")));
        return rentedSpaceList.stream().map(rentedSpace -> modelMapper.map(rentedSpace, RentedSpaceDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public RentedSpaceDTO update(RentedSpaceDTO rentedSpaceDTO, Long id) {
        if (rentedSpaceDTO.getId().equals(id)) {
            if (!rentedSpaceRepo.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found");
            }
            RentedSpace rentedSpace = modelMapper.map(rentedSpaceDTO, RentedSpace.class);
            rentedSpace.setOwner(authService.getCurrentUser());
            return modelMapper.map(rentedSpaceRepo.save(rentedSpace), RentedSpaceDTO.class);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id's didn't match");
        }
    }

    @Transactional
    public void deleteRentedSpace(Long id) {
        rentedSpaceRepo.delete(rentedSpaceRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found")));
    }


}
