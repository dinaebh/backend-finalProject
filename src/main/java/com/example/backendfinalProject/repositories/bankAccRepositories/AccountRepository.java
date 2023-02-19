package com.example.backendfinalProject.repositories.bankAccRepositories;

import com.example.backendfinalProject.models.bankAccount.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {



}
