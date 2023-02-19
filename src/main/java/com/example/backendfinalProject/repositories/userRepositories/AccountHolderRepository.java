package com.example.backendfinalProject.repositories.userRepositories;

import com.example.backendfinalProject.models.user.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {



    AccountHolder findByName();
}
