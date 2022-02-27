package com.innova.project.patikafinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long identityNumber;
    private String name;
    private String surname;
    private Double income;
    private String phoneNumber;
}
