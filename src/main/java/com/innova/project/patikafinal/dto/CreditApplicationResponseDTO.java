package com.innova.project.patikafinal.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditApplicationResponseDTO {
    private CustomerDTO customer;
    private Boolean applicationStatus;
    @JsonInclude(Include.NON_NULL)
    private Double creditLimit;
}
