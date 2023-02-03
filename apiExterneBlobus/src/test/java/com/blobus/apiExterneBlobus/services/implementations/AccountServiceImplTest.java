package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceImplTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountServiceImpl accountService;
    @Mock
    AccountRepository repository;

    @Test
    void createRetailerTransfertAccount() {
        Account account = new Account();
        account.setBalance(10000);
        account.setPhoneNumber("782564426");
        account.setEncryptedPinCode("945952595");
    }
    @Test
    void createCustomerTransfertAccount() {
    }

    @Test
    void getAllTransfertAccount() {
    }

    @Test
    void getTransfertAccountById() {
    }

    @Test
    void enableTransfertAccount() {
    }

    @Test
    void diseableTranfertAccount() {
    }

    @Test
    void getAccountPhoneNumber() {
    }

    @Test
    void updateTranfertAccount() {
    }

    @Test
    void deleteTransfertAccountById() {
    }

    @Test
    void getBalance() {
    }

    @Test
    void modifyTransferAccountRetailer() {
    }
}