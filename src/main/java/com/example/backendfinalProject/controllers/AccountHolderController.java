package com.example.backendfinalProject.controllers;

import com.example.backendfinalProject.services.impl.AccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AccountHolderController {

    @Autowired
    AccountHolderService accountHolderService;

    @PutMapping ("/transfer/{accountId}/{receiverId}")
    @ResponseStatus(HttpStatus.OK)
    public void transferMoney(@PathVariable Long accountId, @PathVariable Long receiverId, @RequestParam BigDecimal money) {
        accountHolderService.transferMoney(accountId, receiverId, money);
    }

    @GetMapping ("/balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal checkBalance (@RequestParam Long accountId, @PathVariable Long userId) {
        return accountHolderService.checkBalance(accountId, userId);
    }


}
