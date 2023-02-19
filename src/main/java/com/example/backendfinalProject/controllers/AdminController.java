package com.example.backendfinalProject.controllers;

import com.example.backendfinalProject.DTOs.AccountDTO;
import com.example.backendfinalProject.models.bankAccount.Account;
import com.example.backendfinalProject.services.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping ("/new-checking-account")
    @ResponseStatus (HttpStatus.CREATED)
    public Account createCheckingAccount (@RequestBody AccountDTO newChecking) {
        return adminService.createCheckingAccount(newChecking);
    }

    @PostMapping ("/new-savings-account")
    @ResponseStatus (HttpStatus.CREATED)
    public Account createSavingAccount (@RequestBody AccountDTO newSaving) {
        return adminService.createSavingsAccount(newSaving);
    }

    @PostMapping ("/new-creditcard")
    @ResponseStatus (HttpStatus.CREATED)
    public Account createCreditCard (@RequestBody AccountDTO newCard) {
        return adminService.createCreditCard(newCard);
    }

    @GetMapping ("/balance/{accountId}")
    @ResponseStatus (HttpStatus.OK)
    public BigDecimal accessBalance (@PathVariable Long accountId) {
        return adminService.accessBalance(accountId);
    }

    @PatchMapping ("/balance/modify/{accountId}")
    @ResponseStatus (HttpStatus.OK)
    public BigDecimal modifyBalance (@PathVariable Long accoundId, @RequestParam BigDecimal newBalance) {
        return adminService.modifyBalance(accoundId, newBalance);
    }

    @DeleteMapping ("/delete/{id}")
    @ResponseStatus (HttpStatus.OK)
    public void deleteAccount (@PathVariable Long accountId) {
        adminService.deleteAccount(accountId);
    }
}
