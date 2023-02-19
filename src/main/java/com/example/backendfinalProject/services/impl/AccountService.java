package com.example.backendfinalProject.services.impl;

import com.example.backendfinalProject.repositories.bankAccRepositories.AccountRepository;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;


}
