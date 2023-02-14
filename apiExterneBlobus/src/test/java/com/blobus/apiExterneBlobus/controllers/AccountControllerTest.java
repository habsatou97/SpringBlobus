/*package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.config.JwtAuthenticationFilter;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.config.SecurityConfiguration;
import com.blobus.apiExterneBlobus.dto.BalanceDto;
import com.blobus.apiExterneBlobus.dto.CreateOrEditAccountDto;
import com.blobus.apiExterneBlobus.dto.GetRetailerBalanceDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.AccountServiceImpl;
import com.blobus.apiExterneBlobus.services.implementations.KeyGeneratorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    ObjectMapper mapper;
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
            .id(1L)
            .firstName("Ramesh")
            .lastName("Fadatare")
            .email("ramesh1@gmail.com")
            .phoneNumber("778545382").build();
    Account account1 = Account.builder()
            .id(1L)
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
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        when(repository.findAll()).thenReturn(List.of(account1,account2));
        ResponseEntity<List<CreateOrEditAccountDto>> accountResult = (new AccountController( new AccountServiceImpl(repository),key)).getAll();
        assertTrue(accountResult.hasBody());
        assertEquals(200, accountResult.getStatusCodeValue());
        assertTrue(accountResult.getHeaders().isEmpty());
        assertTrue(2 == Objects.requireNonNull(accountResult.getBody().size()));
        verify(repository).findAll();

    }

    @Test
    void testGetOne() {
        AccountRepository repository = mock(AccountRepository.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        when(repository.findById((Long) any())).thenReturn(Optional.of(account1));
        ResponseEntity<Optional<CreateOrEditAccountDto>> result = (new AccountController(
                new AccountServiceImpl(repository),key)).getOne(2L);
        assertTrue(result.hasBody());
        assertEquals(200,result.getStatusCodeValue());
        assertTrue(result.getHeaders().isEmpty());
        verify(repository).findById(2L);

    }

    @Test
    void getBalance() throws  Exception{
        AccountRepository repository= mock(AccountRepository.class);
        UserRepository userRepository=mock(UserRepository.class);
        AccountServiceImpl service = mock(AccountServiceImpl.class);
        when(userRepository.save(user)).thenReturn(user);
        when(repository.save(account1)).thenReturn(account1);

        when(service.getBalance(new GetRetailerBalanceDto(
                account1.getEncryptedPinCode(),
                account1.getPhoneNumber(),
                account1.getWalletType()))).
                thenReturn(account1.getBalance());
        org.assertj.core.api.Assertions.assertThat(service.getBalance(new GetRetailerBalanceDto(
                account1.getEncryptedPinCode(),
                account1.getPhoneNumber(),
                account1.getWalletType()))).isNotNull();

    }

    @Test
    void testGetPhoneNumber() {
        AccountRepository repository= mock(AccountRepository.class);
        when(repository.save(account1)).thenReturn(account1);
        AccountServiceImpl service = mock(AccountServiceImpl.class);

        when(service.GetAccountPhoneNumber(account1.getId())).thenReturn(account1.getPhoneNumber());
        org.assertj.core.api.Assertions.assertThat(service.GetAccountPhoneNumber(account1.getId())).isNotNull();
    }

    @Test
    void saveCustomer() throws Exception {
        AccountRepository repository= mock(AccountRepository.class);
        CustomerRepository customerRepository= mock(CustomerRepository.class);
        AccountServiceImpl service= mock(AccountServiceImpl.class);

        customerRepository.save(customer);

        CreateOrEditAccountDto dto = new  CreateOrEditAccountDto();
        dto.setPhoneNumber("788564426");
        dto.setEncryptedPinCode("92952595");
        dto.setWalletType(WalletType.INTERNATIONAL);
        when(service.createCustomerTransfertAccount(dto,customer.getId())).thenReturn(dto);
        org.assertj.core.api.Assertions.assertThat(service.createCustomerTransfertAccount(dto,customer.getId())).isNotNull();
    }

    @Test
    void saveRetailer() {

        AccountRepository repository= mock(AccountRepository.class);
        UserRepository userRepository= mock(UserRepository.class);
        AccountServiceImpl service= mock(AccountServiceImpl.class);

        userRepository.save(user);

        CreateOrEditAccountDto dto = new  CreateOrEditAccountDto();
        dto.setPhoneNumber("788564426");
        dto.setEncryptedPinCode("92952595");
        dto.setWalletType(WalletType.INTERNATIONAL);

        when(service.createRetailerTransfertAccount(dto,user.getId())).thenReturn(dto);
        org.assertj.core.api.Assertions.assertThat(service.createRetailerTransfertAccount(dto,user.getId())).isNotNull();
    }

    @Test
    void update() {
        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        Account ct = new Account();
        ct.setId(1L);
        when(repository.save((Account) any())).thenReturn(ct);
        AccountController controller= new AccountController(service,key);
        CreateOrEditAccountDto dto= new CreateOrEditAccountDto();
        accountRepository.save(account1);
        Optional<Account> account = repository.findById(account1.getId());

        dto.setBalance(100000);
        dto.setPhoneNumber("78541236");
        dto.setEncryptedPinCode("sftruf65489");
        dto.setWalletType(WalletType.BONUS);
        ResponseEntity<CreateOrEditAccountDto> response = controller.update(dto,ct.getId());

        assertEquals(200,response.getStatusCodeValue());
        assertTrue(response.getHeaders().isEmpty());
    }

    @Test
    void enable() {
        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        AccountController controller= new AccountController(service,key);
        Account ct = new Account();
        ct.setId(1L);
        when(repository.save((Account) any())).thenReturn(ct);

        ResponseEntity<CreateOrEditAccountDto> response = controller.enable(ct.getId());
        assertEquals(200,response.getStatusCodeValue());
        assertTrue(response.getHeaders().isEmpty());
    }

    @Test
    void disable() {
        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        AccountController controller= new AccountController(service,key);
        Account ct = new Account();
        ct.setId(1L);

        when(repository.save((Account) any())).thenReturn(ct);

        ResponseEntity<CreateOrEditAccountDto> response = controller.disable(ct.getId());

        assertEquals(200,response.getStatusCodeValue());
        assertTrue(response.getHeaders().isEmpty());
    }

    @Test
    void delete() {
        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        repository.save(account1);
        doNothing().when(repository).deleteById(account1.getId());
        (new AccountController(service,key)).delete(account1.getId());

        org.assertj.core.api.Assertions.assertThat(repository.findById(account1.getId())).isEmpty();
    }

    @Test
    void deleteByPhoneNumber() {

        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        repository.save(account1);
        doNothing().when(repository).deleteAccountByPhoneNumber(account1.getPhoneNumber());
        (new AccountController(service,key)).deleteByPhoneNumber(account1.getPhoneNumber());

        org.assertj.core.api.Assertions.assertThat(repository.getAccountByPhoneNumber(account1.getPhoneNumber())).isEmpty();
    }

    @Test
    void updatedBalance() {
        AccountRepository  repository = mock(AccountRepository.class);
        AccountServiceImpl service =mock(AccountServiceImpl.class);
        KeyGeneratorImpl key = mock(KeyGeneratorImpl.class);
        Account ct = new Account();
        ct.setId(1L);
        when(repository.save((Account) any())).thenReturn(ct);
        AccountController controller= new AccountController(service,key);
        BalanceDto dto = new BalanceDto();
        dto.setBalance(1000000);
        ResponseEntity<BalanceDto> response = controller.updatedBalance(dto,ct.getId());

        assertEquals(200,response.getStatusCodeValue());
        assertTrue(response.getHeaders().isEmpty());

    }
}*/