package com.example.backendfinalProject;

import com.example.backendfinalProject.DTOs.AccountDTO;
import com.example.backendfinalProject.models.bankAccount.Checking;
import com.example.backendfinalProject.models.bankAccount.CreditCard;
import com.example.backendfinalProject.models.bankAccount.Savings;
import com.example.backendfinalProject.models.bankAccount.StudentChecking;
import com.example.backendfinalProject.models.user.AccountHolder;
import com.example.backendfinalProject.models.user.Address;
import com.example.backendfinalProject.models.user.Role;
import com.example.backendfinalProject.repositories.bankAccRepositories.*;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import com.example.backendfinalProject.repositories.userRepositories.AdminRepository;
import com.example.backendfinalProject.repositories.userRepositories.RoleRepository;
import com.example.backendfinalProject.services.impl.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.backendfinalProject.models.enums.Status.ACTIVE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class AdminControllerTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    WebApplicationContext context;


    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    @Autowired
    private SavingsRepository savingsRepository;
    @Autowired
    private CheckingRepository checkingRepository;
    @Autowired
    private StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("Kim SeokWoo", "seokw", "456789",
                LocalDate.of(1996, 8, 07),
                new Address("Gangnam", "2", "00011"),
                new Address("Gangnam", "2", "00011")));
        AccountHolder accountHolder3 = accountHolderRepository.save(new AccountHolder("Lee DongMin", "eunw", "147258",
                LocalDate.of(1997, 03, 30),
                new Address("Jeonju", "5", "11100"),
                new Address("Jeonju", "5", "11100")));
        AccountHolder accountHolder4 = accountHolderRepository.save(new AccountHolder("JaeHyun", "jae", "241023",
                LocalDate.of(2002, 02, 14),
                new Address("Jeonju", "5", "11100"),
                new Address("Jeonju", "5", "11100")));

        accountHolderRepository.saveAll(List.of(accountHolder1, accountHolder2, accountHolder3, accountHolder4));


        Savings saving = savingsRepository.save(new Savings(
                new BigDecimal( 2000), accountHolder1, accountHolder2,
                LocalDate.of(2023,01,05),
                ACTIVE, new BigDecimal(0.2), new BigDecimal(250), "SFS251F"));

        Checking checking = checkingRepository.save(new Checking(
                new BigDecimal(4500), accountHolder2, accountHolder3,
                LocalDate.of(2022,12,10),
                ACTIVE, "ASR4151F"));

        StudentChecking studentChecking = studentCheckingRepository.save(new StudentChecking(
                new BigDecimal(2000), accountHolder4, accountHolder2,
                LocalDate.of(2020,3,12),
                ACTIVE, "HTY6254A"));

        CreditCard creditCard = creditCardRepository.save(new CreditCard(
                new BigDecimal(1000), accountHolder3, null,
                LocalDate.of(2023,02,02),
                new BigDecimal(3600), new BigDecimal(0.12)));

        accountRepository.saveAll(List.of(saving, checking, studentChecking, creditCard));
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();


    }

    @Test
    void create_checkingAcc() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        Checking checking = new Checking(
                new BigDecimal( 2000), accountHolder1, null,
                LocalDate.of(2023,01,05),
                ACTIVE,"SFS251F");

        //convert to Json
        String body = objectMapper.writeValueAsString(checking);
        MvcResult result = mockMvc.perform(post("/new-checking-account").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Dina El Badri"));
    }

    @Test
    void create_savingsAcc() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        Savings savings = savingsRepository.save(new Savings(
                new BigDecimal( 2000), accountHolder1, accountHolder1,
                LocalDate.of(2023,01,05),
                ACTIVE, new BigDecimal(0.2), new BigDecimal(250), "SFS251F"));

        //convert to Json
        String body = objectMapper.writeValueAsString(savings);
        MvcResult result = mockMvc.perform(post("/new-savings-account").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Dina El Badri"));


    }

    @Test
    void create_creditCard() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        CreditCard creditCard = creditCardRepository.save(new CreditCard(
                new BigDecimal(1000), accountHolder1, null,
                LocalDate.of(2023,02,02),
                new BigDecimal(3600), new BigDecimal(0.12)));

        //convert to Json
        String body = objectMapper.writeValueAsString(creditCard);
        MvcResult result = mockMvc.perform(post("/new-creditcard").content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Dina El Badri"));
    }


    @Test
    void accessBalance_usingAccountId() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        Checking checking = checkingRepository.save(new Checking(
                new BigDecimal( 2000), accountHolder1, null,
                LocalDate.of(2023,01,05),
                ACTIVE, "SFS251F"));

        //convert to Json
        String body = objectMapper.writeValueAsString(checking);

        MvcResult result = mockMvc.perform(get("/balance/" + checking.getAccountId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(BigDecimal.valueOf(5000).toString()));
    }

    @Test
    void modifyBalance_withAccId() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050")));

        Checking checking = checkingRepository.save(new Checking(
                new BigDecimal( 2000), accountHolder1, null,
                LocalDate.of(2023,01,05),
                ACTIVE, "SFS251F"));

        //convert to Json
        String body = objectMapper.writeValueAsString(checking);
        MvcResult result = (MvcResult) mockMvc.perform(patch("/balance/modify/" + checking.getAccountId() + "newBalance=2000")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(BigDecimal.valueOf(7000).toString()));
    }
}
