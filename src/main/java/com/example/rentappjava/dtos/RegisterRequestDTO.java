package com.example.rentappjava.dtos;

import lombok.Data;


@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
}
