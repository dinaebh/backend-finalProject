package com.example.backendfinalProject.repositories.bankAccRepositories;

import com.example.backendfinalProject.models.bankAccount.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {



}
