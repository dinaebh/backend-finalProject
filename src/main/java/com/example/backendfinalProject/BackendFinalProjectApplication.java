package com.example.backendfinalProject;


import com.example.backendfinalProject.models.bankAccount.Checking;
import com.example.backendfinalProject.models.bankAccount.CreditCard;
import com.example.backendfinalProject.models.bankAccount.Savings;
import com.example.backendfinalProject.models.user.*;
import com.example.backendfinalProject.repositories.bankAccRepositories.AccountRepository;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import com.example.backendfinalProject.repositories.userRepositories.AdminRepository;
import com.example.backendfinalProject.repositories.userRepositories.ThirdPartyRepository;
import com.example.backendfinalProject.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class BackendFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendFinalProjectApplication.class, args);
	}

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	private ThirdPartyRepository thirdPartyRepository;

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_THIRDPARTY"));

			userService.saveUser(new User(null, "John Doe", "john", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "James Smith", "james", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Jane Carry", "jane", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "Chris Anderson", "chris", "1234", new ArrayList<>()));

			userService.addRoleToUser("john", "ROLE_USER");
			userService.addRoleToUser("james", "ROLE_ADMIN");
			userService.addRoleToUser("jane", "ROLE_USER");
			userService.addRoleToUser("chris", "ROLE_ADMIN");
			userService.addRoleToUser("chris", "ROLE_USER");

		};
	}

}
