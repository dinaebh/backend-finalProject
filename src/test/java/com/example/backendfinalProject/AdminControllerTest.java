package com.example.backendfinalProject;

import com.example.backendfinalProject.DTOs.AccountDTO;
import com.example.backendfinalProject.DTOs.UserDTO;
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
import com.example.backendfinalProject.repositories.userRepositories.UserRepository;
import com.example.backendfinalProject.services.impl.AdminService;
import com.example.backendfinalProject.services.impl.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
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

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;
    AccountHolder accountHolder1;
    AccountHolder accountHolder2;
    AccountHolder accountHolder3;
    AccountHolder accountHolder4;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        accountHolder1 = new AccountHolder("Dina El Badri", "dinaebh", passwordEncoder.encode("123456"),
                LocalDate.of(1994, 2, 12),
                new Address("Daal", "5", "08050"),
                new Address("Daal", "5", "08050"));

        accountHolder2 = new AccountHolder("Kim SeokWoo", "seokw", passwordEncoder.encode("456789"),
                LocalDate.of(1996, 8, 07),
                new Address("Gangnam", "2", "00011"),
                new Address("Gangnam", "2", "00011"));

        accountHolder3 = new AccountHolder("Lee DongMin", "eunw", passwordEncoder.encode("147258"),
                LocalDate.of(1997, 03, 30),
                new Address("Jeonju", "5", "11100"),
                new Address("Jeonju", "5", "11100"));

        accountHolder4 = new AccountHolder("Jung JaeHyun", "jae", passwordEncoder.encode("241023"),
                LocalDate.of(2002, 02, 14),
                new Address("Jeonju", "5", "11100"),
                new Address("Jeonju", "5", "11100"));

        accountHolderRepository.save(accountHolder1);
        accountHolderRepository.save(accountHolder2);
        accountHolderRepository.save(accountHolder3);
        accountHolderRepository.save(accountHolder4);

        userService.addRoleToUser("dinaebh", "ROLE_USER");
        userService.addRoleToUser("seokw", "ROLE_USER");
        userService.addRoleToUser("eunw", "ROLE_USER");
        userService.addRoleToUser("jae", "ROLE_USER");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void create_checkingAcc() throws Exception {

        AccountDTO checkingDTO = new AccountDTO(new BigDecimal(2000), accountHolder1.getId(),
                null, LocalDate.now(), null,null,
                "AGSD123", null, null, null, ACTIVE );

        //convert to Json
        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult result = mockMvc.perform(post("/new-checking-account").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Dina El Badri"));
    }

    @Test
    void create_savingsAcc() throws Exception {

        AccountDTO savingsDTO = new AccountDTO(new BigDecimal(2000), accountHolder2.getId(),
                null, LocalDate.now(), new BigDecimal(100),null,
                "AGSD123", null, null, new BigDecimal(0.2), ACTIVE );

        //convert to Json
        String body = objectMapper.writeValueAsString(savingsDTO);
        MvcResult result = mockMvc.perform(post("/new-savings-account").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Kim SeokWoo"));


    }

    @Test
    void create_creditCard() throws Exception {

        AccountDTO creditCardDTO = new AccountDTO(new BigDecimal(2000), accountHolder3.getId(),
                null, LocalDate.now(), null,null,
                null, null, new BigDecimal(5000), new BigDecimal(0.15), null );

        //convert to Json
        String body = objectMapper.writeValueAsString(creditCardDTO);
        MvcResult result = mockMvc.perform(post("/new-creditcard").content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Lee DongMin"));
    }


    @Test
    void accessBalance_usingAccountId() throws Exception {

        Checking checking = checkingRepository.save(new Checking(new BigDecimal(5000), accountHolder4, null,
                LocalDate.of(2023, 01, 01), ACTIVE, "GHD789"));

        //convert to Json

        MvcResult result = mockMvc.perform(get("/balance/?accountId=" + checking.getAccountId())).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(BigDecimal.valueOf(5000).toString()));
    }

    @Test
    void modifyBalance_withAccId() throws Exception {

        Checking checking = checkingRepository.save(new Checking(new BigDecimal(5000), accountHolder1, null,
                LocalDate.of(2023, 01, 01), ACTIVE, "GHD789"));

        //convert to Json

        MvcResult result = (MvcResult) mockMvc.perform(patch("/balance/modify?accountId=" + checking.getAccountId() + "newBalance=2000")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(BigDecimal.valueOf(7000).toString()));
    }

    @Test
    void createAdmin_user() throws Exception {
        UserDTO adminDTO = new UserDTO("Cho MiYeon", "MiYeon", "123456");


        String body = objectMapper.writeValueAsString(adminDTO);
        MvcResult result = mockMvc.perform(post( "/create-admin").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("MiYeon"));
    }

    @Test
    void create_accountHolder() throws Exception {
        UserDTO accountHolderDTO = new UserDTO("Jang Gyuri", "Gyurious", "541526");


        String body = objectMapper.writeValueAsString(accountHolderDTO);
        MvcResult result = mockMvc.perform(post( "/create-accountholder").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Gyurious"));
    }

    @Test
    void create_thirdParty() throws Exception {
        UserDTO thirdParty = new UserDTO("Kim JiSoo", "JiSoo", "854126");


        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post( "/create-thirdparty").content(body)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("JiSoo"));

    }

}
