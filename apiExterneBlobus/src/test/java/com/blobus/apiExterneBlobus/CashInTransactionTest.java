package com.blobus.apiExterneBlobus;

import com.blobus.apiExterneBlobus.config.JwtAuthenticationFilter;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.config.SecurityConfiguration;
import com.blobus.apiExterneBlobus.controllers.TransactionController;
import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.TransferAccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.interfaces.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.blobus.apiExterneBlobus.models.enums.Role.RETAILER;
import static com.blobus.apiExterneBlobus.models.enums.TransactionCurrency.XOF;
import static com.blobus.apiExterneBlobus.models.enums.TransactionStatus.SUCCESS;
import static com.blobus.apiExterneBlobus.models.enums.TransactionType.CASHIN;
import static com.blobus.apiExterneBlobus.models.enums.WalletType.*;
import static java.lang.Boolean.TRUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CashInTransactionTest {
    @Autowired
    public MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    TransferAccountRepository transferAccountRepository;
    @MockBean
    TransactionService transactionService;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AccountRepository accountRepository;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    JwtService jwtService;
    @MockBean
    SecurityConfiguration securityConfiguration;


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCashInTransaction() throws Exception {
        Customer customer1 = new Customer();
        customer1.setFirstName("Moussa");
        customer1.setLastName("Diop");
        customer1.setEmail("moussa@gmail.com");
        customer1.setPhoneNumber("773000000");
        Customer customerSave1 = customerRepository.save(customer1);

        Account accountCustomer1 = new  Account();
        accountCustomer1.setBalance(10000.0);
        accountCustomer1.setPhoneNumber("773000000");
        accountCustomer1.setEncryptedPinCode("ert33");
        accountCustomer1.setWalletType(BONUS);
        accountCustomer1.set_active(true);
        accountCustomer1.setCustomer(customerSave1);
        transferAccountRepository.save(accountCustomer1);
        customer1.addTransferAccounts(accountCustomer1);
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Issa");
        customer2.setLastName("Fall");
        customer2.setEmail("issa@gmail.com");
        customer2.setPhoneNumber("773000001");
        Customer customerSave2 = customerRepository.save(customer2);

        Account accountCustomer2 = new  Account();
        accountCustomer2.setBalance(5000.0);
        accountCustomer2.setPhoneNumber("773000001");
        accountCustomer2.setEncryptedPinCode("ert34");
        accountCustomer2.setWalletType(PRINCIPAL);
        accountCustomer2.set_active(true);
        accountCustomer2.setCustomer(customerSave2);
        transferAccountRepository.save(accountCustomer2);
        customer2.addTransferAccounts(accountCustomer2);
        customerRepository.save(customer2);

        User retailer = new User();
        retailer.setFirstName("Aly");
        retailer.setLastName("Fall");
        retailer.setEmail("aly@gmail.com");
        retailer.setNinea("ninea");
        retailer.setPhoneNumber("779999999");
        retailer.setUserId("secret_d");
        retailer.setUserSecret("secret_client");
        retailer.addRoles(RETAILER);
        User retailerSave = userRepository.save(retailer);

        Account accountRetailer = new  Account();
        accountRetailer.setBalance(20000.0);
        accountRetailer.setPhoneNumber("773000006");
        accountRetailer.setEncryptedPinCode("ert35");
        accountRetailer.setWalletType(INTERNATIONAL);
        accountRetailer.set_active(true);
        accountRetailer.setRetailer(retailer);
        transferAccountRepository.save(accountRetailer);
        retailer.addTransferAccounts(accountRetailer);
        userRepository.save(retailer);




        AmountDto amountDto = new AmountDto(11000.0,XOF);
        CustomerDto customerDto = new CustomerDto("773000000",BONUS);
        RetailerDto retailerDto = new RetailerDto("773000006","ert35",INTERNATIONAL);

        RequestBodyTransactionDto requestBodyTransactionDto = new RequestBodyTransactionDto(
                amountDto,
                customerDto,
                retailerDto,
                "ref123",
                TRUE,
                LocalDate.of(2023,02,02),
                CASHIN
        );

        ResponseCashInTransactionDto responseCashInTransactionDto = ResponseCashInTransactionDto
                .builder()
                .status(SUCCESS)
                .transactionId(1L)
                .build();

        Mockito.when(transactionService.CashInTransaction(requestBodyTransactionDto)).thenReturn(responseCashInTransactionDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/ewallet/v1/cashins").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                //.header(HttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbCIsImlhdCI6MTY3NTM1ODYyNCwiZXhwIjoxNjc1MzYwMDY0fQ.J_HNwr_romXql1KBuXTKZTSXm6-Np7lcCcE7Hg3Ldr0")
                .content(this.mapper.writeValueAsString(requestBodyTransactionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['status']", is("SUCCESS")));
    }

}
