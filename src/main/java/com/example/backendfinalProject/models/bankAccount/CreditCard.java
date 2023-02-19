package com.example.backendfinalProject.models.bankAccount;

import com.example.backendfinalProject.models.user.AccountHolder;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class CreditCard extends Account {
    private BigDecimal creditLimit = BigDecimal.valueOf(100);
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);

    private LocalDate lastInterestDate = LocalDate.now();

    public CreditCard() {
    }

    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, BigDecimal creditLimit, BigDecimal interestRate) {
        super(primaryOwner, secondaryOwner, creationDate);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
        setBalance(balance);
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.valueOf(100000)) <= 0 && creditLimit.compareTo(BigDecimal.valueOf(100)) >= 0) {
            this.creditLimit = creditLimit;
        } else {
            System.err.println("Credit limit must be between 100 and 100000. Will be set by default to 100000");
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
            System.err.println("Interest rate must be between 0.1 and 0.2. Will be set by default to 0.1");
            this.interestRate = BigDecimal.valueOf(0.1);
        }
    }

    public void addInterestRate() {
        if(Period.between(lastInterestDate, LocalDate.now()).getMonths() >= 1) {
            setBalance(getBalance().multiply(getInterestRate().divide(BigDecimal.valueOf(12))
                    .multiply(BigDecimal.valueOf(Period.between(lastInterestDate, LocalDate.now()).getMonths())).add(getBalance())));
            lastInterestDate.plusMonths(Period.between(lastInterestDate, LocalDate.now()).getMonths());
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
}
