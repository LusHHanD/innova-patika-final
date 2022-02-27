package com.innova.project.patikafinal.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long identityNumber;
    private String name;
    private String surname;
    private Double income;
    private String phoneNumber;
}
