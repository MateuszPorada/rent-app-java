package com.example.rentappjava.dtos;

import com.example.rentappjava.entities.User;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {
    private Long id;
    private Double price;
    private List<String> imgUrls;
    private Long flatID;
    private User tenantID;
}
