package com.example.rentappjava.dtos;

import lombok.Data;

@Data
public class RentActionDTO {
    private Long id;
    private String tenantEmail;
    private Long rentedSpaceID;

}
