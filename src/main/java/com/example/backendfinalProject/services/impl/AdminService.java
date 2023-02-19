package com.example.backendfinalProject.services.impl;

import com.example.backendfinalProject.DTOs.AccountDTO;
import com.example.backendfinalProject.models.bankAccount.*;
import com.example.backendfinalProject.models.user.AccountHolder;
import com.example.backendfinalProject.repositories.bankAccRepositories.*;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import com.example.backendfinalProject.repositories.userRepositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;


    //Creation of checking account.
    public Account createCheckingAccount(AccountDTO newChecking) {

        if (accountHolderRepository.findById(newChecking.getPrimaryOwner()).isPresent()) {
            AccountHolder primaryOwner = accountHolderRepository.findById(newChecking.getPrimaryOwner()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "The account holder you are looking for doesn't exist in the database"));
            AccountHolder secondaryOwner = null;
            if (newChecking.getSecondaryOwner() != null && accountHolderRepository.findById(newChecking.getSecondaryOwner()).isPresent()) {
                secondaryOwner = accountHolderRepository.findById(newChecking.getSecondaryOwner()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "The account holder you are looking for doesn't exist in the database"));
            }
            Integer age = Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears();
            StudentChecking studentAccount;

            if (age > 24) {
                Checking checkingAccount = new Checking(newChecking.getBalance(), primaryOwner, secondaryOwner,
                        newChecking.getCreationDate(), newChecking.getStatus(), newChecking.getSecretKey());

                return checkingRepository.save(checkingAccount);
            } else {
                System.err.println("Customer age should be over 24 years to create a checking account. Otherwise, create a student Checking account.");
                studentAccount = new StudentChecking(newChecking.getBalance(), primaryOwner, secondaryOwner,
                        newChecking.getCreationDate(), newChecking.getStatus(), newChecking.getSecretKey());
            }
            return studentCheckingRepository.save(studentAccount);
        }

        throw new IllegalArgumentException("Can't find any account holder with this id");

    }

    //Creation of savings account.
    public Savings createSavingsAccount(AccountDTO newSaving) {

        if (accountHolderRepository.findById(newSaving.getPrimaryOwner()).isPresent()) {
            AccountHolder primaryOwner = accountHolderRepository.findById(newSaving.getPrimaryOwner()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "The account holder you are looking for doesn't exist in the database"));
            AccountHolder secondaryOwner = null;
            if (newSaving.getSecondaryOwner() != null && accountHolderRepository.findById(newSaving.getSecondaryOwner()).isPresent()) {
                secondaryOwner = accountHolderRepository.findById(newSaving.getSecondaryOwner()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "The account holder you are looking for doesn't exist in the database"));
            }

            Savings savingAccount = new Savings(newSaving.getBalance(), primaryOwner, secondaryOwner,
                    newSaving.getCreationDate(), newSaving.getStatus(), newSaving.getInterestRate(), newSaving.getMinimumBalance(), newSaving.getSecretKey());
            return savingsRepository.save(savingAccount);
        }

        throw new IllegalArgumentException("Can't find any account holder with this id");
    }

    //Creation of credit card.
    public CreditCard createCreditCard(AccountDTO newCard) {
        if (accountHolderRepository.findById(newCard.getPrimaryOwner()).isPresent()) {
            AccountHolder primaryOwner = accountHolderRepository.findById(newCard.getPrimaryOwner()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "The account holder you are looking for doesn't exist in the database"));
            AccountHolder secondaryOwner = null;
            if (newCard.getSecondaryOwner() != null && accountHolderRepository.findById(newCard.getSecondaryOwner()).isPresent()) {
                secondaryOwner = accountHolderRepository.findById(newCard.getSecondaryOwner()).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "The account holder you are looking for doesn't exist in the database"));
            }
            CreditCard creditCard = new CreditCard(newCard.getBalance(), primaryOwner, secondaryOwner,
                    newCard.getCreationDate(), newCard.getCreditLimit(), newCard.getInterestRate());

            return creditCardRepository.save(creditCard);
        }

        throw new IllegalArgumentException("Can't find any account holder with this id");
    }

    //Admins should be able to access the balance for any account and to modify it.
    public BigDecimal accessBalance(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            Account foundAccount = accountRepository.findById(accountId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "The account you are looking for doesn't exist in the database"));
            accountRepository.save(foundAccount);
            return foundAccount.getBalance();
        }
        throw new IllegalArgumentException("Can't find any account matching with this Id");
    }

    //Admins can access and modify.
    public BigDecimal modifyBalance(Long accountId, BigDecimal newBalance) {
        if (accountRepository.findById(accountId).isPresent()) {
            Account foundAccount = accountRepository.findById(accountId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "The account you are looking for doesn't exist in the database"));
            foundAccount.setBalance(newBalance);
            accountRepository.save(foundAccount);
            return foundAccount.getBalance();
        }
        throw new IllegalArgumentException("Can't find any account matching with this Id");
    }

    //Delete account.
    public void deleteAccount (Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            accountRepository.deleteById(accountId);
        }
        throw new IllegalArgumentException("Can't find any account matching with this Id");
    }
}
