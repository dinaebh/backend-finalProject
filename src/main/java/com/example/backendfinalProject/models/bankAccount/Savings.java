package com.example.backendfinalProject.models.bankAccount;

import com.example.backendfinalProject.models.enums.Status;
import com.example.backendfinalProject.models.user.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
public class Savings extends Account {

    //Savings accounts have a default interest rate
    @NotNull
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    //Savings accounts should have default minimumBalance value.
    @NotNull
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);

    private String secretKey;

    private LocalDate lastInterestDate = LocalDate.now();

    public Savings() {
    }

    public Savings(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, LocalDate creationDate, Status status, BigDecimal interestRate, BigDecimal minimumBalance, String secretKey) {
        super(balance, primaryOwner, secondaryOwner, creationDate, status);
        setInterestRate(interestRate);
        setMinimumBalance(minimumBalance);
        setSecretKey(secretKey);
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    //Savings accounts may be instantiated with an interest rate other than the default, with a maximum interest rate of 0.5
    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(new BigDecimal(0.5)) >= 0) {
            this.interestRate = interestRate;
        } else {
            System.err.println("Interest rate should be higher than 0.0025 but lower than 0.5");
            this.interestRate = new BigDecimal(0.5);
        }
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    // A minimum balance of less than 1000 but no lower than 100
    public void setMinimumBalance(BigDecimal minimumBalance) {
        if (minimumBalance.compareTo(new BigDecimal(100)) > 0) {
            this.minimumBalance = minimumBalance;
        } else {
            System.err.println("Minimum balance can't lower than 100, it's gonna be set by default to 100.");
            this.minimumBalance = new BigDecimal(100);
        }
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    //add interest every year

    public void addInterest() {
        if(Period.between(lastInterestDate, LocalDate.now()).getMonths() >= 1){
            setBalance(getBalance().multiply(getInterestRate())
                    .multiply(BigDecimal.valueOf(Period.between(lastInterestDate, LocalDate.now()).getYears())).add(getBalance()));
            lastInterestDate.plusYears(Period.between(lastInterestDate, LocalDate.now()).getYears());
        }
    }

    @Override
    public  void setBalance (BigDecimal balance){
        addInterest();
        if (balance.compareTo(BigDecimal.valueOf(250)) < 0) {
            super.setBalance(balance = minimumBalance.subtract(BigDecimal.valueOf(40)));
        } else{
            super.setBalance(balance);
        }
    }

    @Override
    public BigDecimal getBalance() {
        addInterest();
        return super.getBalance();
    }
}
