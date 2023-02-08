package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.config.JwtAuthenticationFilter;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.config.SecurityConfiguration;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @MockBean
    AccountRepository accountRepository;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    UserController userController;
    @MockBean
    JwtService jwtService;
    @MockBean
    SecurityConfiguration securityConfiguration;


    @Autowired
    private WebApplicationContext webApplicationContext;



    User user= new  User(
            3L,
            "El Seydi",
            "Ba",
            "em-seydi.ba@avimtoo.com" ,
            Role.RETAILER,
            "vimto1245",
            "718954362",
            "fzivbedfegjd",
            "fohfgfyf78");
    Customer customer = Customer.builder()
            .firstName("Ramesh")
            .lastName("Fadatare")
            .email("ramesh1@gmail.com")
            .phoneNumber("778545382").build();
    Account account1 = Account.builder()
            .balance(100000)
            .encryptedPinCode("945142952595")
            .phoneNumber("762564426")
            .walletType(WalletType.SALAIRE)
            .is_active(true)
            .retailer(user)
            .build();
    Account account2 = Account.builder()
            .balance(200000)
            .encryptedPinCode("945192457412595")
            .phoneNumber("752568426")
            .walletType(WalletType.BONUS)
            .is_active(true)
            .customer(customer)
            .build();


    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAll() {
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findAll()).thenReturn(List.of(account1,account2));
        ResponseEntity<List<Account>> accountResult = (new AccountController( new AccountServiceImpl(repository))).getAll();
        assertTrue(accountResult.hasBody());
        assertEquals(200, accountResult.getStatusCodeValue());
        assertTrue(accountResult.getHeaders().isEmpty());
        assertTrue(2 == Objects.requireNonNull(accountResult.getBody().size()));
        verify(repository).findAll();

    }

    @Test
    void testGetOne() {
        AccountRepository repository = mock(AccountRepository.class);
        when(repository.findById((Long) any())).thenReturn(Optional.of(account1));
        ResponseEntity<Account> result = (new AccountController(new AccountServiceImpl(repository))).getOne(2L);
        assertTrue(result.hasBody());
        assertEquals(200,result.getStatusCodeValue());
        assertTrue(result.getHeaders().isEmpty());
        verify(repository).findById(2L);

    }

   /* @Test
    void getBalance() throws  Exception{
        AccountRepository repository= mock(AccountRepository.class);
        UserRepository userRepository=mock(UserRepository.class);
        AccountServiceImpl service = mock(AccountServiceImpl.class);
        when(userRepository.save(user)).thenReturn(user);
        when(repository.save(account1)).thenReturn(account1);

        when(service.getBalance(
                account1.getEncryptedPinCode(),
                account1.getPhoneNumber(),
                account1.getRetailer().getId())).thenReturn(account1);
        org.assertj.core.api.Assertions.assertThat(service.getBalance(
                account1.getEncryptedPinCode(),
                account1.getPhoneNumber(),
                account1.getRetailer().getId())).isNotNull();

    }*/



    @Test
    void getPhoneNumber() {
    }

    @Test
    void saveCustomer() {
    }

    @Test
    void saveRetailer() {
    }

    @Test
    void update() {
    }

    @Test
    void enable() {
    }

    @Test
    void disable() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByPhoneNumber() {
    }

    @Test
    void updatedBalance() {
    }
}