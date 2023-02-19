package com.example.backendfinalProject.repositories.userRepositories;

import com.example.backendfinalProject.models.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {



}
