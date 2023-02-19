package com.example.backendfinalProject.repositories.bankAccRepositories;

import com.example.backendfinalProject.models.bankAccount.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {



}
