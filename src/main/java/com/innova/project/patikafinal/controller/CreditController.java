package com.innova.project.patikafinal.controller;

import com.innova.project.patikafinal.Exception.CreditNotFoundException;
import com.innova.project.patikafinal.Exception.CustomerNotFoundException;
import com.innova.project.patikafinal.dto.CreditApplicationResponseDTO;
import com.innova.project.patikafinal.dto.CreditDTO;
import com.innova.project.patikafinal.dto.ExceptionDTO;
import com.innova.project.patikafinal.service.impl.CreditServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/credit")
public class CreditController {
    private final CreditServiceImpl creditService;

    public CreditController(CreditServiceImpl creditService) {
        this.creditService = creditService;
    }


    @GetMapping
    private ResponseEntity<List<CreditDTO>> getAllCreditApplications() {
        return ResponseEntity.ok(creditService.getAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<CreditDTO> getByCreditId(@PathVariable Long id) {
        return ResponseEntity.ok(creditService.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    private ResponseEntity<List<CreditDTO>> getByCustomerIdentityNumber(@PathVariable Long customerId) {
        return ResponseEntity.ok(creditService.getByCustomerIdentityNumber(customerId));
    }

    @PostMapping
    private ResponseEntity<CreditApplicationResponseDTO> saveCredit(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("identityNumber")) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(creditService.apply((Long) body.get("identityNumber")));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteById(@PathVariable Long id) {
        creditService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({CreditNotFoundException.class, CustomerNotFoundException.class})
    private ResponseEntity<ExceptionDTO> handleCreditNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDTO.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .message(e.getMessage())
                        .build()
                );
    }
}
