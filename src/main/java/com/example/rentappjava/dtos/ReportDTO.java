package com.example.rentappjava.dtos;

import lombok.Data;

@Data
public class ReportDTO {
    private Long id;
    private String topic;
    private String message;
    private Boolean isDone;
    private Long tenantId;
    private Long rentActionId;
}
