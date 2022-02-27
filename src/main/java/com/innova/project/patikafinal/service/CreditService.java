package com.innova.project.patikafinal.service;

import com.innova.project.patikafinal.dto.CreditApplicationResponseDTO;
import com.innova.project.patikafinal.dto.CreditDTO;
import com.innova.project.patikafinal.entity.Credit;

import java.util.List;

public interface CreditService {
    List<CreditDTO> getAll();

    CreditDTO getById(Long id);

    List<CreditDTO> getByCustomerIdentityNumber(Long id);

    CreditApplicationResponseDTO apply(Long id);

    void deleteById(Long id);

    CreditDTO toDto(Credit credit);

    Credit toEntity(CreditDTO creditDTO);
}
