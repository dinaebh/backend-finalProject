package com.example.backendfinalProject.services.impl;

import com.example.backendfinalProject.models.bankAccount.Account;
import com.example.backendfinalProject.models.bankAccount.Checking;
import com.example.backendfinalProject.models.bankAccount.Savings;
import com.example.backendfinalProject.models.bankAccount.StudentChecking;
import com.example.backendfinalProject.models.user.ThirdParty;
import com.example.backendfinalProject.repositories.bankAccRepositories.AccountRepository;
import com.example.backendfinalProject.repositories.userRepositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private AccountRepository accountRepository;

    //Send money from third party
    public void sendMoney(String hashedKey, Long accountId, Long secretKey, BigDecimal money) {

        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);

            Account account = accountRepository.findById(accountId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Account not found in the database"));

        if(account instanceof Checking){
            Checking checkingAccount = (Checking) account;
            if(!checkingAccount.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

        if(account instanceof StudentChecking){
            StudentChecking studentAccount = (StudentChecking) account;
            if(!studentAccount.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

        if(account instanceof Savings){
            Savings savingsAccount = (Savings) account;
            if(!savingsAccount.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

            account.setBalance(account.getBalance().add(money));
    }

    //Receive money from third party.
    public void receiveMoney (String hashedKey, Long accountId, Long secretKey, BigDecimal money) {
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey(hashedKey);
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account not found in the database"));

        if(account instanceof Checking) {
            Checking checkingAccount = (Checking) account;
            if(!checkingAccount.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

        if(account instanceof StudentChecking) {
            StudentChecking studentChecking = (StudentChecking) account;
            if(!studentChecking.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

        if(account instanceof Savings) {
            Savings savingsAccount = (Savings) account;
            if(!savingsAccount.getSecretKey().equals(secretKey)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The secret key does not match");
        }

        account.setBalance(account.getBalance().subtract(money));
    }
}
