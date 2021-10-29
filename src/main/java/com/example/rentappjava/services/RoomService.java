package com.example.rentappjava.services;

import com.example.rentappjava.dtos.RoomDTO;
import com.example.rentappjava.entities.Room;
import com.example.rentappjava.repos.FlatRepo;
import com.example.rentappjava.repos.RoomRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {

    private final RoomRepo roomRepo;
    private final FlatRepo flatRepo;
    private final ModelMapper modelMapper;

    public RoomDTO getRoomById(Long id) {
        return modelMapper.map(roomRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found")), RoomDTO.class);
    }

    public RoomDTO save(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setFlat(flatRepo.findById(roomDTO.getFlatID()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found")));
        return modelMapper.map(roomRepo.save(room), RoomDTO.class);
    }

    public List<RoomDTO> getRoomByFlatId(Long flatId) {
        List<Room> roomList = roomRepo.findAllByFlat(flatRepo.findById(flatId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flat not found")));
        return roomList.stream().map(room -> modelMapper.map(room, RoomDTO.class)).collect(Collectors.toList());
    }

    public void deleteRoom(Long id) {
        roomRepo.delete(roomRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found")));
    }
}
