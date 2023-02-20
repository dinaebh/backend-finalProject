package com.example.backendfinalProject;

import com.example.backendfinalProject.models.bankAccount.Checking;
import com.example.backendfinalProject.models.user.AccountHolder;
import com.example.backendfinalProject.models.user.Address;
import com.example.backendfinalProject.models.user.ThirdParty;
import com.example.backendfinalProject.repositories.bankAccRepositories.AccountRepository;
import com.example.backendfinalProject.repositories.bankAccRepositories.CheckingRepository;
import com.example.backendfinalProject.repositories.userRepositories.AccountHolderRepository;
import com.example.backendfinalProject.repositories.userRepositories.ThirdPartyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.backendfinalProject.models.enums.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyControllerTest {

    @Autowired
    WebApplicationContext context;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private CheckingRepository checkingRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ThirdParty thirdParty = thirdPartyRepository.save(new ThirdParty("Kim SeokWoo", "RoWoon", "123456", "24513FFS"));
    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
    }

    @Test
    void thirdParty_sendMoney() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),

                new Address("Daal", "5", "08050")));

        Checking checking = checkingRepository.save(new Checking(
                new BigDecimal( 2000), accountHolder1, null,
                LocalDate.of(2023,01,05),
                ACTIVE,"SFS251F"));

        ThirdParty thirdParty = thirdPartyRepository.save(new ThirdParty("Kim SeokWoo", "RoWoon", "123456", ""));

        //convert to Json
        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/transfer/24513FFS" + checking.getAccountId() + "secretKey=SFS251F?money=2000").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(new BigDecimal(4000), checking.getBalance());
    }

    @Test
    void thirdParty_receiveMoney() throws Exception {

        AccountHolder accountHolder1 = accountHolderRepository.save(new AccountHolder("Dina El Badri", "dinaebh", "123456",
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),

                new Address("Daal", "5", "08050")));

        Checking checking = new Checking(
                new BigDecimal( 2500), accountHolder1, null,
                LocalDate.of(2023,01,05),
                ACTIVE,"SFS251F");

        ThirdParty thirdParty = thirdPartyRepository.save(new ThirdParty("Kim SeokWoo", "RoWoon", "123456", "24513FFS"));

        //convert to Json
        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/transfer-receive/24513FFS" + checking.getAccountId() + "secretKey=SFS251F?money=2000").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertEquals(new BigDecimal(500), checking.getBalance());
    }


}
