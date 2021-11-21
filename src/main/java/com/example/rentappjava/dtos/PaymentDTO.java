package com.example.rentappjava.dtos;

import lombok.Data;

import java.time.Instant;

@Data
public class PaymentDTO {
    private Long id;
    private float amount;
    private Instant timestamp;
    private Long rentActionID;
    private Long tenantID;

}
