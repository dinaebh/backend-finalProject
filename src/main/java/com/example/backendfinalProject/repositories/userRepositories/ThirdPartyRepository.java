package com.example.backendfinalProject.repositories.userRepositories;

import com.example.backendfinalProject.models.user.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {


    ThirdParty findByHashedKey(String hashedKey);
}
