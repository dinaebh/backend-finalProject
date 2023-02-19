package com.example.backendfinalProject.repositories.bankAccRepositories;

import com.example.backendfinalProject.models.bankAccount.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {



}
