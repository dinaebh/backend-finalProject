package com.example.backendfinalProject.models.bankAccount;

import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class StudentChecking extends Account {

    public StudentChecking() {

    }

    public StudentChecking(BigDecimal balance, String primaryOwner, LocalDate creationDate) {
        super(balance, primaryOwner, creationDate);
    }
}
