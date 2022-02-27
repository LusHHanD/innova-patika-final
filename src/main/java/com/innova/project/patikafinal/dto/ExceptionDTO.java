package com.innova.project.patikafinal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDTO {
    private String message;
    private int code;
}
