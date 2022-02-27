package com.innova.project.patikafinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Customer {
    @Id
    private Long identityNumber;
    private String name;
    private String surname;
    private Double income;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Credit> credits;
}
