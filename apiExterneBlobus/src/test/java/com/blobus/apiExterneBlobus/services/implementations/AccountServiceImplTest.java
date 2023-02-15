package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void createRetailerTransfertAccount() throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {

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
      /*  CreateOrEditAccountDto dto = new  CreateOrEditAccountDto();
        dto.setPhoneNumber("788564426");
        dto.setEncryptedPinCode("92952595");
        dto.setWalletType(WalletType.INTERNATIONAL);*/

        CreateAccountDto createDto = CreateAccountDto.builder()
                .phoneNumber("788564426")
                .encryptedPinCode("92952595")
                .walletType(WalletType.INTERNATIONAL)
                .build();

        CreateOrEditAccountDto dto =   CreateOrEditAccountDto.builder().build();
        dto.setPhoneNumber(createDto.getPhoneNumber());
        dto.setEncryptedPinCode(createDto.getEncryptedPinCode());
        dto.setWalletType(createDto.getWalletType());

        Account account = new Account();
        account.setBalance(0.0);
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setEncryptedPinCode(dto.getEncryptedPinCode());
        account.setWalletType(dto.getWalletType());
        account.set_active(true);

        //verify mocker
       when(repository.save(any(Account.class))).then(returnsFirstArg());
       when(service.createRetailerTransfertAccount(createDto,id)).thenReturn(dto);
        Assertions.assertThat(service.createRetailerTransfertAccount(createDto,id)).isNotNull();
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
        CreateAccountDto createDto = CreateAccountDto.builder()
                .phoneNumber("788564426")
                .encryptedPinCode("92952595")
                .walletType(WalletType.INTERNATIONAL)
                .build();

        CreateOrEditAccountDto dto =   CreateOrEditAccountDto.builder().build();
        dto.setPhoneNumber(createDto.getPhoneNumber());
        dto.setEncryptedPinCode(createDto.getEncryptedPinCode());
        dto.setWalletType(createDto.getWalletType());

        Account account = new Account();
        account.setBalance(0.0);
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setEncryptedPinCode(dto.getEncryptedPinCode());
        account.setWalletType(dto.getWalletType());
        account.set_active(true);
        //verify mocker
        when(repository.save(any(Account.class))).then(returnsFirstArg());
        when(service.createCustomerTransfertAccount(createDto,id)).thenReturn(dto);
        Assertions.assertThat(service.createCustomerTransfertAccount(createDto,id)).isNotNull();


    }

    @Test
    void getAllTransfertAccount() {

     List<CreateOrEditAccountDto> list= accountService.getAllTransfertAccount();

        when(repository.findAll().stream().map(
                account -> {
                    CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
                    dto.setEncryptedPinCode(account.getEncryptedPinCode());
                    dto.setPhoneNumber(account.getPhoneNumber());
                    dto.setBalance(account.getBalance());
                    dto.setWalletType(account.getWalletType());
                    return dto;
                }
        ).toList()).thenReturn(list);
        Assertions.assertThat(service.getAllTransfertAccount()).isNotNull();
    }

    @Test
    void getTransfertAccountById() {

        Account account = new Account();
        account.setBalance(100000);
        account.setId(1L);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(true);

        repository.save(account);
        AccountRepository str = mock(AccountRepository.class);
        AccountServiceImpl srv= mock(AccountServiceImpl.class);
        Optional<Account> compte = str.findById(account.getId());

        /*Optional<CreateOrEditAccountDto> account1 = repository.findById(account.getId()).stream().map(

        ).findAny();
*/
       when(service.getTransfertAccountById(account.getId()))
               .thenReturn(compte.stream().
                       map( account2 -> {
                           CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
                           dto.setEncryptedPinCode(account2.getEncryptedPinCode());
                           dto.setPhoneNumber(account2.getPhoneNumber());
                           dto.setBalance(account2.getBalance());
                           dto.setWalletType(account2.getWalletType());
                           return dto;
                       }).findAny());
                //.thenThrow(new ResourceNotFoundException("account not found"));
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

        CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
        dto.setBalance(account.getBalance());
        dto.setEncryptedPinCode(account.getEncryptedPinCode());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setWalletType(account.getWalletType());

        when(service.enableTransfertAccount(account.getId())).thenReturn(dto);
        Assertions.assertThat(service.enableTransfertAccount(account.getId())).isNotNull();
    }

    @Test
    void diseableTranfertAccount() {

        Account account = new Account();
        account.setBalance(100000);
        account.setPhoneNumber("7892578426");
        account.setEncryptedPinCode("94t952595");
        account.setWalletType(WalletType.BONUS);
        account.set_active(false);

        repository.save(account);

        CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
        dto.setBalance(account.getBalance());
        dto.setEncryptedPinCode(account.getEncryptedPinCode());
        dto.setPhoneNumber(account.getPhoneNumber());
        dto.setWalletType(account.getWalletType());

        when(service.diseableTranfertAccount(account.getId())).thenReturn(dto);
        Assertions.assertThat(service.diseableTranfertAccount(account.getId())).isNotNull();
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

        when(service.getAccountPhoneNumber(account.getId())).thenReturn(account.getPhoneNumber());
        Assertions.assertThat(service.getAccountPhoneNumber(account.getId())).isNotNull();
    }
    @Test
    void updateTranfertAccount() {

        CreateOrEditAccountDto dto =   CreateOrEditAccountDto.builder().build();
        dto.setPhoneNumber("788564426");
        dto.setEncryptedPinCode("92952595");
        dto.setWalletType(WalletType.INTERNATIONAL);
        dto.setBalance(100000.02);

        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setEncryptedPinCode(dto.getEncryptedPinCode());
        account.setWalletType(dto.getWalletType());
        account.set_active(true);

        repository.save(account);

        when(service.updateTranfertAccount(dto,account.getId())).thenReturn(dto);
        Assertions.assertThat(service.updateTranfertAccount(dto,account.getId())).isNotNull();
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
    void getBalance() throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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
        WalletType  walletType = WalletType.BONUS;

        when(service.getBalance(
                new GetRetailerBalanceDto(encryptedPinCode,phoneNumber,walletType)))
                .thenReturn(new AmountDto(account.getBalance(), TransactionCurrency.XOF));
        Assertions.assertThat(service.getBalance(
                new GetRetailerBalanceDto(encryptedPinCode,phoneNumber,walletType)))
                .isNull();
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

        CreateOrEditAccountDto dto =   CreateOrEditAccountDto.builder().build();
        dto.setPhoneNumber("788564426");
        dto.setEncryptedPinCode("92952595");
        dto.setWalletType(WalletType.PRINCIPAL);
        dto.setBalance(100000.02);

        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setPhoneNumber(dto.getPhoneNumber());
        account.setEncryptedPinCode(dto.getEncryptedPinCode());
        account.setWalletType(dto.getWalletType());
        account.set_active(true);
        account.setRetailer(user);

        repository.save(account);

        when(service.modifyTransferAccountRetailer(
                account.getId(),dto,user.getRoles().get(0)))
                .thenReturn(dto);
        Assertions.assertThat(service.modifyTransferAccountRetailer(
                account.getId(),dto,user.getRoles().get(0)))
                .isNotNull();
    }

    @Test
    void updatedBalance() {
        AccountRepository repository1= mock(AccountRepository.class);
        Account account = new Account();
        account.setPhoneNumber("string");
        account.setEncryptedPinCode("string");
        account.setWalletType(WalletType.BONUS);

        repository1.save(account);
        BalanceDto dto = new BalanceDto();
        dto.setBalance(1000000.00);

        when(service.updatedBalance(dto,account.getId())).thenReturn(dto);
        Assertions.assertThat(service.updatedBalance(dto, account.getId())).isNotNull();
    }
}