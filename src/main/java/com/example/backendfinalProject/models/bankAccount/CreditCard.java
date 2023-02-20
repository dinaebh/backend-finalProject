package com.example.backendfinalProject.models.bankAccount;

import com.example.backendfinalProject.models.enums.Status;
import com.example.backendfinalProject.models.user.AccountHolder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account {
    private BigDecimal creditLimit = BigDecimal.valueOf(100);
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate lastInterestDate = LocalDate.now();

    public CreditCard() {
    }

    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner, creationDate, Status.ACTIVE);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(@NotNull BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.valueOf(100000)) <= 0 && creditLimit.compareTo(BigDecimal.valueOf(100)) >= 0) {
            this.creditLimit = creditLimit;
        } else {
            System.err.println("Credit limit must be between 100 and 100000. Will be set to default: 100000");
            this.creditLimit = BigDecimal.valueOf(100000);
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.valueOf(0.1)) < 0) {
            this.interestRate = interestRate;
        } else  {
            System.err.println("Interest rate must be between 0.1 and 0.2. Will be set to default: 0.1");
            this.interestRate = BigDecimal.valueOf(0.1);
        }
    }

    public void addInterestRate() {
        if (LocalDate.now().getMonthValue() > getLastInterestDate().getMonthValue() && LocalDate.now().getYear() >= getLastInterestDate().getYear()) {
            for (int i = LocalDate.now().getMonthValue(); i > getLastInterestDate().getMonthValue() ; i--) {
                this.setBalance(this.getBalance().multiply(interestRate.add(BigDecimal.valueOf(1))));
            }
            setLastInterestDate(LocalDate.now());
        }
    }

    @Override
    public BigDecimal getBalance() {
        addInterestRate();
        return super.getBalance();
    }

    @Override
    public void setBalance(BigDecimal balance) {
        addInterestRate();
        super.setBalance(balance);
    }

    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
