package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AccountServiceImplTest {
    @MockBean
    UserRepository userRepository;

    @MockBean
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

        Long id = user.getId();
        // j'unitialise un compte e transfert
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);

        //verify mocker
       when(repository.save(any(Account.class))).then(returnsFirstArg());
       when(service.createRetailerTransfertAccount(account,id)).thenReturn(account);
        Assertions.assertThat(service.createRetailerTransfertAccount(account,id)).isNotNull();
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
       when(service.createCustomerTransfertAccount(account,id)).thenReturn(account);
        Assertions.assertThat(service.createCustomerTransfertAccount(account,id)).isNotNull();


    }

    @Test
    void getAllTransfertAccount() {

     List<Account> list= accountService.getAllTransfertAccount();

        when(repository.findAll()).thenReturn(list);
        Assertions.assertThat(service.getAllTransfertAccount()).isNotNull();
    }

    @Test
    void getTransfertAccountById() {
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);
       repository.save(account);
       when(service.getTransfertAccountById(account.getId())).thenReturn(account);
        Assertions.assertThat(service.getTransfertAccountById(account.getId())).isNotNull();

    }

    @Test
    void enableTransfertAccount() {

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);
        repository.save(account);
        when(service.EnableTransfertAccount(account.getId())).thenReturn(account);
        Assertions.assertThat(service.EnableTransfertAccount(account.getId())).isNotNull();
    }

    @Test
    void diseableTranfertAccount() {


        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);
        repository.save(account);
        when(service.DiseableTranfertAccount(account.getId())).thenReturn(account);
        Assertions.assertThat(service.DiseableTranfertAccount(account.getId())).isNotNull();

    }

    @Test
    void getAccountPhoneNumber() {

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);
        repository.save(account);
        when(service.GetAccountPhoneNumber(account.getId())).thenReturn(account.getPhoneNumber());
        Assertions.assertThat(service.GetAccountPhoneNumber(account.getId())).isNotNull();

    }

    @Test
    void updateTranfertAccount() {
        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("762564426");
        account.setEncryptedPinCode("945142952595");
        account.setWalletType(WalletType.PRINCIPAL);
        account.set_active(true);

        repository.save(account);
        when(service.updateTranfertAccount(account,account.getId())).thenReturn(account);
        Assertions.assertThat(service.updateTranfertAccount(account,account.getId())).isNotNull();
    }

    @Test
    void deleteTransfertAccountById() {

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("762564426");
        account.setEncryptedPinCode("945142952595");
        account.setWalletType(WalletType.PRINCIPAL);
        account.set_active(true);
        repository.save(account);
        Mockito.when(repository.findById(account.getId())).thenReturn(Optional.of(account));
    }

    @Test
    void getBalance() {
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
        userRepository.save(user);

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("762564426");
        account.setEncryptedPinCode("945142952595");
        account.setWalletType(WalletType.PRINCIPAL);
        account.set_active(true);
        account.setRetailer(user);
        repository.save(account);

        String encryptedPinCode = account.getEncryptedPinCode();
        String phoneNumber =account.getPhoneNumber();
        Long idUser = account.getRetailer().getId();
       when(service.getBalance(encryptedPinCode,phoneNumber,idUser)).thenReturn(account);
        Assertions.assertThat(service.getBalance(encryptedPinCode,phoneNumber,idUser)).isNotNull();
    }

    @Test
    void modifyTransferAccountRetailer() {

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
        userRepository.save(user);

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("762564426");
        account.setEncryptedPinCode("945142952595");
        account.setWalletType(WalletType.PRINCIPAL);
        account.setRetailer(user);
        account.set_active(true);
        repository.save(account);

        when(service.modifyTransferAccountRetailer(account.getId(),account,user.getRoles().get(0))).thenReturn(account);
        Assertions.assertThat(service.modifyTransferAccountRetailer(account.getId(),account,user.getRoles().get(0))).isNotNull();
    }
}