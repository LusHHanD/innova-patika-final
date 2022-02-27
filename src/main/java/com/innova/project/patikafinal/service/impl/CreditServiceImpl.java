package com.innova.project.patikafinal.service.impl;

import com.innova.project.patikafinal.Exception.CreditNotFoundException;
import com.innova.project.patikafinal.creditscore.CreditScoreCalculatorService;
import com.innova.project.patikafinal.dto.CreditApplicationResponseDTO;
import com.innova.project.patikafinal.dto.CreditDTO;
import com.innova.project.patikafinal.dto.CustomerDTO;
import com.innova.project.patikafinal.entity.Credit;
import com.innova.project.patikafinal.repository.CreditRepository;
import com.innova.project.patikafinal.service.CreditService;
import com.innova.project.patikafinal.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditServiceImpl implements CreditService {
    private final CreditRepository creditRepository;
    private final CreditScoreCalculatorService creditScoreCalculatorService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public CreditServiceImpl(CreditRepository creditRepository, CreditScoreCalculatorService creditScoreCalculatorService, CustomerService customerService, ModelMapper modelMapper) {
        this.creditRepository = creditRepository;
        this.creditScoreCalculatorService = creditScoreCalculatorService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CreditDTO> getAll() {
        List<Credit> creditList = new ArrayList<>(creditRepository.findAll());

        return creditList.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CreditDTO getById(Long id) {
        return creditRepository.findById(id).map(this::toDto).orElseThrow(CreditNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        creditRepository.deleteById(id);
    }

    @Override
    public List<CreditDTO> getByCustomerIdentityNumber(Long id) {
        return creditRepository.findByCustomer_IdentityNumber(id)
                .map(list -> {
                    if (list.size() == 0) throw new CreditNotFoundException();
                    else return list;
                })
                .map(list -> list.stream().
                        map(this::toDto)
                        .collect(Collectors.toList())
                ).orElseThrow(CreditNotFoundException::new);
    }

    @Override
    public CreditApplicationResponseDTO apply(Long id) {
        CustomerDTO customerDTO = customerService.getById(id);
        Double creditLimit = creditScoreCalculatorService.calculate(customerDTO);
        Boolean applicationStatus = getStatus(creditLimit);

        Credit credit = Credit.builder()
                .creditAmount(creditLimit)
                .customer(customerService.toEntity(customerDTO))
                .status(applicationStatus)
                .build();

        creditRepository.save(credit);

        return CreditApplicationResponseDTO.builder()
                .customer(customerDTO)
                .applicationStatus(applicationStatus)
                .creditLimit(applicationStatus == Boolean.FALSE ? null : creditLimit)
                .build();
    }

    private Boolean getStatus(Double creditLimit) {
        return creditLimit == 0D ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public CreditDTO toDto(Credit credit) {
        return modelMapper.map(credit, CreditDTO.class);
    }

    @Override
    public Credit toEntity(CreditDTO creditDTO) {
        return modelMapper.map(creditDTO, Credit.class);
    }
}
