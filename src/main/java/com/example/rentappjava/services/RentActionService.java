package com.example.rentappjava.services;

import com.example.rentappjava.dtos.RentActionDTO;
import com.example.rentappjava.entities.RentAction;
import com.example.rentappjava.entities.RentedSpace;
import com.example.rentappjava.entities.User;
import com.example.rentappjava.repos.RentActionRepo;
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
public class RentActionService {
    private final RentActionRepo rentActionRepo;
    private final UserRepo userRepo;
    private final RentedSpaceRepo rentedSpaceRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public RentActionDTO createRentAction(RentActionDTO rentActionDTO) {
        RentAction rentAction = modelMapper.map(rentActionDTO, RentAction.class);
        rentAction.setTenant(userRepo.findById(rentActionDTO.getTenantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
        rentAction.setRentedSpace(rentedSpaceRepo.findById(rentActionDTO.getRentedSpaceId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found")));
        return modelMapper.map(rentActionRepo.save(rentAction), RentActionDTO.class);
    }

    public List<RentActionDTO> getRentActionBySpaceID(Long id) {
        RentedSpace rentedSpace = rentedSpaceRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found"));
        return rentedSpace.getRentAction().stream().map(rentAction -> modelMapper.map(rentAction, RentActionDTO.class)).collect(Collectors.toList());
    }

    public List<RentActionDTO> getRentActionByTenantID(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getRentActions().stream().map(rentAction -> modelMapper.map(rentAction, RentActionDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public RentActionDTO update(RentActionDTO rentActionDTO, Long id) {
        if (rentActionDTO.getId().equals(id)) {
            if (!rentedSpaceRepo.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found");
            }
            RentAction rentAction = modelMapper.map(rentActionDTO, RentAction.class);
            rentAction.setRentedSpace(rentedSpaceRepo.findById(rentActionDTO.getRentedSpaceId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found")));
            rentAction.setTenant(userRepo.findById(rentActionDTO.getTenantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
            return modelMapper.map(rentActionRepo.save(rentAction), RentActionDTO.class);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Id's didn't match");
        }
    }

    public void deleteActionById(Long id) {
        rentActionRepo.delete(rentActionRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Action not found")));
    }


}

