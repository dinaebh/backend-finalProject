package com.example.backendfinalProject.repositories.bankAccRepositories;

import com.example.backendfinalProject.models.bankAccount.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {



}
