package com.example.backendfinalProject.services.impl;

import com.example.backendfinalProject.models.bankAccount.Account;
import com.example.backendfinalProject.models.user.AccountHolder;
import com.example.backendfinalProject.repositories.bankAccRepositories.AccountRepository;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import com.example.backendfinalProject.repositories.userRepositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    //Transfer money
    public void transferMoney(Long senderAccountId, Long receiverAccountId, BigDecimal money) {
        if(accountRepository.findById(senderAccountId).isPresent()){
        Account account = accountRepository.findById(senderAccountId).get();

            if(account == null) throw new IllegalArgumentException("This account does not exist");
            if(account.getBalance().compareTo(money) < 0) throw new IllegalArgumentException("Insufficient balance. Can't continue with the transaction");

            if(accountRepository.findById(receiverAccountId).isPresent()) {
                Account account1 = accountRepository.findById(receiverAccountId).get();

                account1.setBalance(account1.getBalance().add(money));
                account.setBalance(account.getBalance().subtract(money));
                accountRepository.save(account);
                accountRepository.save(account1);
            }

            throw new IllegalArgumentException("Receiver account not found");
        }

        throw new IllegalArgumentException("Sender account not found");
    }

    //Check your balance
    public BigDecimal checkBalance(Long accountId, Long userId) {
        Account chosenAccount = null;
        if(userRepository.findById(userId).isPresent()) {
            AccountHolder accountHolder = accountHolderRepository.findById(userId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Can't find the account Id"));

            for(Account acc : accountHolder.getPrimaryOwnershipList()) {
                if(acc.getAccountId() == accountId) {
                    chosenAccount = accountRepository.findById(accountId).orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Can't find the account Id"));
                } else {
                    throw new IllegalArgumentException("You don't have access to this account");
                }
            }
        } else {
            throw new IllegalArgumentException("Can't find an account with this Id");
        }
        return chosenAccount.getBalance();
    }
}
