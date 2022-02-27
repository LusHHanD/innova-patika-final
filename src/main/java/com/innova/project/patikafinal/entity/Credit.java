package com.innova.project.patikafinal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Credit {
    @Id  @GeneratedValue(strategy =GenerationType.IDENTITY )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "identity_number")
    private Customer customer;
    private Double creditAmount;
    private Boolean status;
}
