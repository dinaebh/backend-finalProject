package com.example.backendfinalProject.controllers;

import com.example.backendfinalProject.services.impl.AccountHolderService;
import com.example.backendfinalProject.services.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountHolderService accountHolderService;

}
