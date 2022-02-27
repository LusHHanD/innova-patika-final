package com.innova.project.patikafinal.repository;

import com.innova.project.patikafinal.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {
    Optional<List<Credit>> findByCustomer_IdentityNumber(Long id);
}
