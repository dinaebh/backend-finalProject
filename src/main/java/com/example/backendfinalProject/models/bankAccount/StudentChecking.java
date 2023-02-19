package com.example.backendfinalProject.models.bankAccount;

import com.example.backendfinalProject.models.enums.Status;
import com.example.backendfinalProject.models.user.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class StudentChecking extends Account {

    private String secretKey;

    public StudentChecking() {

    }

    public StudentChecking(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status);
        setSecretKey(secretKey);
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
