package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceImplTest1 {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountServiceImpl accountService;

    @MockBean
    AccountServiceImpl service;
    @Mock
    AccountRepository repository;


    @Test
    void createRetailerTransfertAccount() {

        // initialise un retailer
        String email ="dtdvf.fffrgfby@avimtoo.com";
        User user = new User();
        user.setNinea("vimto1245");
        user.setPhoneNumber("8454426");
        user.setUserId("fzi512d");
        user.setUserSecret("ee578");
        user.setLastName("Diouf");
        user.setFirstName("Aby");
        user.setEmail(email);
        user.setRoles(Collections.singletonList(Role.RETAILER));

        // je persiste ce retailer dans la base de données
        userRepository.save(user);

        // recupere le reatiler
        User user1= userRepository.findById(user.getId()).orElseThrow();
        //
        Long id = user1.getId();
        // j'unitialise un compte e transfert
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);

        //verify mocker
       when(repository.save(any(Account.class))).then(returnsFirstArg());

        // je teste la methode dans le services
       Account account1= accountService.createRetailerTransfertAccount(account,id);
       // verifyb argument
       assertNotNull(account1.getPhoneNumber());

    }
    @Test
    void createCustomerTransfertAccount() {

        // initialise un retailer
        String email ="laye.faye@avimtoo.com";
        Customer costumer = new Customer();
        costumer.setPhoneNumber("788654426");
        costumer.setLastName("Faye");
        costumer.setFirstName("Ablaye");
        costumer.setEmail(email);

        // je persiste ce retailer dans la base de données
        customerRepository.save(costumer);

        // recupere le reatiler
        Customer customer1= customerRepository.findById(costumer.getId()).orElseThrow();
        //
        Long id = costumer.getId();
        // j'unitialise un compte e transfert
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("788564426");
        account.setEncryptedPinCode("92952595");
        account.setWalletType(WalletType.INTERNATIONAL);
        account.set_active(true);
        //verify mocker
        when(repository.save(any(Account.class))).then(returnsFirstArg());

        // je teste la methode dans le services
        Account account1= accountService.createCustomerTransfertAccount(account,id);
        // verifyb argument
        assertNotNull(account1.getPhoneNumber());
    }

    @Test
    void getAllTransfertAccount() {

     List<Account> list= accountService.getAllTransfertAccount();

        when(repository.findAll()).thenReturn(list);
    }

    @Test
    void getTransfertAccountById() {
        assertThrows(EntityNotFoundException.class,
                ()-> accountService.getTransfertAccountById(1L));

    }

    @Test
    void enableTransfertAccount() {
        assertThrows(EntityNotFoundException.class,
                ()-> accountService.EnableTransfertAccount(1L));
    }

    @Test
    void diseableTranfertAccount() {
        assertThrows(EntityNotFoundException.class,
                () -> accountService.DiseableTranfertAccount(1L));
    }

    @Test
    void getAccountPhoneNumber() {
            assertThrows(EntityNotFoundException.class,
                    ()-> accountService.GetAccountPhoneNumber(1L));
    }

    @Test
    void updateTranfertAccount() {
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("762564426");
        account.setEncryptedPinCode("945142952595");
        account.setWalletType(WalletType.PRINCIPAL);
        account.set_active(true);
        assertThrows(EntityNotFoundException.class,
                (() -> accountService.updateTranfertAccount(account,1L)));
    }

    @Test
    void deleteTransfertAccountById() {

        Account account = accountRepository.findById(52L).orElseThrow();
       Mockito.when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
    }

    @Test
    void getBalance() {
        Account account = accountRepository.findById(52L).orElseThrow();

        String encryptedPinCode = account.getEncryptedPinCode();
        String phoneNumber =account.getPhoneNumber();
        Long idUser = account.getRetailer().getId();
       when(service.getBalance(encryptedPinCode,phoneNumber,idUser)).thenReturn(account);
    }

    @Test
    void modifyTransferAccountRetailer() {
    }
}