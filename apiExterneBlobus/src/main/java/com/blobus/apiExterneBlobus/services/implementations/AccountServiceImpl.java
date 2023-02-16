package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.*;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.interfaces.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository transferAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeyGeneratorImpl keyGeneratorService;

    public  AccountServiceImpl(AccountRepository transferAccountRepository){
        this.transferAccountRepository=transferAccountRepository;
    }

    @Override
    public CreateOrEditAccountDto createRetailerTransfertAccount(CreateAccountDto transferAccount,Long id) {
        Account account = new Account();
        User retailer = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        List<Account> comptes = retailer.getAccounts();
        int i = 0;
        /* verifier si le retailer à deja un compte de tranfert
         si la collection compts est vide
         on ajoute le compte directement
        */
        if (comptes.isEmpty()) {
            // verifie si l'utlisateur est un retailer
            if (retailer.getRoles().contains(Role.RETAILER)) {

                if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() ==9
                        && transferAccount.getWalletType()!=null
                        && transferAccount.getEncryptedPinCode()!=null
                        && transferAccount.getEncryptedPinCode().length() >0){
                    account.setRetailer(retailer);
                    account.set_active(true);
                    account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                    account.setWalletType(transferAccount.getWalletType());
                    account.setPhoneNumber(transferAccount.getPhoneNumber());
                    account.setBalance(0.0);
                    Account compte = transferAccountRepository.save(account);
                    retailer.addTransferAccounts(compte);
                    CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                            .balance(account.getBalance())
                            .encryptedPinCode(transferAccount.getEncryptedPinCode())
                            .phoneNumber(transferAccount.getPhoneNumber())
                            .walletType(transferAccount.getWalletType())
                            .build();
                    return dto;
                }
                throw new IllegalStateException("Veuillez renseignez les données correctement");


            } else throw new EntityNotFoundException("Retailer with id" + ": " + id + " don't exist");

        }
        // Si le retailer a deja au moins un compte :
        else {

            for (Account cpt : comptes ) {
                WalletType type = cpt.getWalletType();
                if (type == transferAccount.getWalletType())
                    i++;
            }
            if (i != 0) {
                //System.out.println("Ce customer possede deja un compte de ce type");
                throw  new IllegalStateException("Ce retailer possede deja un compte de ce type");
            } else {
                if (retailer.getRoles().contains(Role.RETAILER)) {

                    if(transferAccount.getPhoneNumber()!=null &&
                            transferAccount.getPhoneNumber().length() ==9
                            && transferAccount.getWalletType()!=null
                            && transferAccount.getEncryptedPinCode()!=null
                            && transferAccount.getEncryptedPinCode().length() >0){
                        account.setRetailer(retailer);
                        account.set_active(true);
                        account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                        account.setWalletType(transferAccount.getWalletType());
                        account.setPhoneNumber(transferAccount.getPhoneNumber());
                        account.setBalance(0.0);
                        Account compte = transferAccountRepository.save(account);
                        retailer.addTransferAccounts(compte);
                        CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                                .balance(account.getBalance())
                                .encryptedPinCode(transferAccount.getEncryptedPinCode())
                                .phoneNumber(transferAccount.getPhoneNumber())
                                .walletType(transferAccount.getWalletType())
                                .build();
                        return dto;

                    } throw new IllegalStateException("Veuillez renseignez les donnez correctement");

                } else throw new EntityNotFoundException("Retailer with id" + ": " + id + " don't exist");
            }
        }
    }

    @Override
    public CreateOrEditAccountDto createCustomerTransfertAccount(CreateAccountDto transferAccount, Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Customer not found"));
        Account account= new Account();
        List<Account> comptes = customer.getTransferAccounts();
        int i = 0;
        for (Account cpt : comptes
        ) {
            WalletType type=cpt.getWalletType();
            if(type==transferAccount.getWalletType())
                i++;
        }
        if (i != 0) {
            throw new IllegalStateException("Ce customer : "  +id + " Possede deja un compte de ce type");
        } else {

            if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() ==9
              && transferAccount.getWalletType()!=null && transferAccount.getEncryptedPinCode()!=null
                    && transferAccount.getEncryptedPinCode().length() >0){
                account.setCustomer(customer);
                account.set_active(true);
                account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                account.setWalletType(transferAccount.getWalletType());
                account.setPhoneNumber(transferAccount.getPhoneNumber());
                account.setBalance(0.0);
                Account compte = transferAccountRepository.save(account);
                customer.addTransferAccounts(compte);
                CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                        .balance(account.getBalance())
                        .encryptedPinCode(transferAccount.getEncryptedPinCode())
                        .phoneNumber(transferAccount.getPhoneNumber())
                        .walletType(transferAccount.getWalletType())
                        .build();
                return dto;
            }
            throw new IllegalStateException("Veuillez renseignez les donnez correctement");

        }
    }

    @Override
    public List <CreateOrEditAccountDto> getAllTransfertAccount() {

        return transferAccountRepository.findAll().stream().map( account -> {
            CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
            dto.setPhoneNumber(account.getPhoneNumber());
            dto.setWalletType(account.getWalletType());
            dto.setEncryptedPinCode(account.getEncryptedPinCode());
            dto.setBalance(account.getBalance());
            return dto;
        }).toList();
    }

    @Override
    public Optional<CreateOrEditAccountDto> getTransfertAccountById(Long id) {
        return transferAccountRepository.findById(id).stream().map(account -> {
            CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
            dto.setPhoneNumber(account.getPhoneNumber());
            dto.setWalletType(account.getWalletType());
            dto.setEncryptedPinCode(account.getEncryptedPinCode());
            dto.setBalance(account.getBalance());
            return dto;
        }).findAny();

    }


    @Override
    public CreateOrEditAccountDto enableTransfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(TRUE);
            dto.setPhoneNumber(existingAccount.get().getPhoneNumber());
            dto.setWalletType(existingAccount.get().getWalletType());
            dto.setEncryptedPinCode(existingAccount.get().getEncryptedPinCode());
            dto.setBalance(existingAccount.get().getBalance());
            transferAccountRepository.save(existingAccount.get());
            return dto;
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public CreateOrEditAccountDto diseableTranfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();

        if (existingAccount.isPresent()) {
            existingAccount.get().set_active(FALSE);
            dto.setPhoneNumber(existingAccount.get().getPhoneNumber());
            dto.setWalletType(existingAccount.get().getWalletType());
            dto.setEncryptedPinCode(existingAccount.get().getEncryptedPinCode());
            dto.setBalance(existingAccount.get().getBalance());
            transferAccountRepository.save(existingAccount.get());
            return dto;
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public String getAccountPhoneNumber(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent())
            return existingAccount.get().getPhoneNumber();
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    /**
     * Cette methode permet de modifer un compteb de transfert
     * @param transferAccount
     * @param id
     * @return
     */
    @Override
    public CreateOrEditAccountDto updateTranfertAccount(EditAccountDto transferAccount, Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            if(transferAccount.getBalance()!=0)
            {
           existingAccount.get().setBalance(transferAccount.getBalance());
            }

           if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() >0
                   && !Objects.equals(transferAccount.getPhoneNumber(),existingAccount.get().getPhoneNumber()))
           {
            existingAccount.get().setEncryptedPinCode(transferAccount.getEncryptedPinCode());
           }
           Account compte = transferAccountRepository.save(existingAccount.get());
               CreateOrEditAccountDto account =  CreateOrEditAccountDto.builder().build();
               account.setBalance(compte.getBalance());
               account.setWalletType(existingAccount.get().getWalletType());
               account.setEncryptedPinCode(compte.getEncryptedPinCode());
               account.setPhoneNumber(compte.getPhoneNumber());
               return account;
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
        //return null;
    }

    @Override
    public void deleteTransfertAccountById(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            transferAccountRepository.deleteById(id);
        }
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    @Override
    public AmountDto getBalance(GetRetailerBalanceDto getRetailerBalanceDto) throws NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Account account = transferAccountRepository.findByPhoneNumberAndWalletType( getRetailerBalanceDto.getPhoneNumber(),
                getRetailerBalanceDto.getWalletType()).orElseThrow();
        String pinCode = keyGeneratorService.decrypt(new DecryptDto(account.getEncryptedPinCode()));


        if (!pinCode.equals(getRetailerBalanceDto.getEncryptedPinCode())){
            throw new IllegalStateException("Les deux code pinde ne correspodent pas.");
        }
        double balance = transferAccountRepository.findByPhoneNumberAndWalletType(
                getRetailerBalanceDto.getPhoneNumber(),
                getRetailerBalanceDto.getWalletType()).orElseThrow().getBalance();
        return new AmountDto(balance, TransactionCurrency.XOF);
    }

    /**
     * Cette methode permet a un administrateur de mettre à jour le compte de tranfert d'un retailer
     * @param id
     * @param account
     * @param role
     * @return
     */
    @Override
    public CreateOrEditAccountDto modifyTransferAccountRetailer(Long id,
                                                                EditAccountDto account
                                                                ) {

        Account account1 = transferAccountRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Account not found"));


        if(account1.getRetailer().getRoles().contains(Role.RETAILER)){

            account1.setBalance(account.getBalance());
            account1.setPhoneNumber(account.getPhoneNumber());
            account1.setEncryptedPinCode(account.getEncryptedPinCode());
            Account account2= transferAccountRepository.saveAndFlush(account1);
            CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                    .walletType(account1.getWalletType())
                    .balance(account2.getBalance())
                    .encryptedPinCode(account2.getEncryptedPinCode())
                    .phoneNumber(account2.getPhoneNumber())
                    .build();
             return dto;
        }
       throw new EntityNotFoundException("This user isn't a retailer");
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        Optional<Account> account = transferAccountRepository.getAccountByPhoneNumber(phoneNumber);
        if (account.isPresent()){
            Long id = transferAccountRepository.getAccountByPhoneNumber(phoneNumber).get().getId();
            transferAccountRepository.deleteById(id);
        }

    }

    /**
     * Cette methode permet de mettre à jour le solde d'un compte de transfert
     * @param balance
     * @param id
     * @return
     */
    @Override
    public BalanceDto updatedBalance(BalanceDto balance, Long id) {
        Optional<Account> account = transferAccountRepository.findById(id);
        if (account.isPresent()){
            account.get().setBalance(balance.getBalance());
            transferAccountRepository.saveAndFlush(account.get());
            return balance;
        }
        throw new EntityNotFoundException("This account doesn't exist !!");
    }

    @Override
    public ResponseChangePinCodeDto changePinCode(RequestBodyChangePinCodeDto requestBodyChangePinCodeDto, String msisdn, CustomerType customerType, WalletType walletType,String content_type)
            throws NoSuchPaddingException,
                 IllegalBlockSizeException,
                 IOException,
                 NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

        KeyDto keyDto = new KeyDto();
        KeyDto keyDto1=new KeyDto();
        DecryptDto decryptDto=new DecryptDto();
        ResponseChangePinCodeDto responseChangePinCodeDto = new ResponseChangePinCodeDto();

        Optional<Account> account = transferAccountRepository.findByPhoneNumberAndWalletType(msisdn,walletType);
        if(customerType!= CustomerType.RETAILER){
            throw new EntityNotFoundException("Only a retailer is allow to do this operation !!");
        }
        else if(!account.isPresent()) {
            responseChangePinCodeDto.setErrorCode("2000");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;
        } else if (msisdn.length() != 9) {
            responseChangePinCodeDto.setErrorCode(ErrorCode.CUSTOMER_MSISDN_IS_INVALID.getErrorCode());
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (requestBodyChangePinCodeDto == null) {
            responseChangePinCodeDto.setErrorCode("21");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (content_type==null) {
            responseChangePinCodeDto.setErrorCode("25");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (requestBodyChangePinCodeDto.getEncryptedNewPinCode() == null ||
                requestBodyChangePinCodeDto.getEncryptedPinCode() == null) {
            responseChangePinCodeDto.setErrorCode("22");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;
        } else if (msisdn==null|| customerType==null || walletType==null) {
            responseChangePinCodeDto.setErrorCode("27");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else {

            decryptDto.setEncryptedPinCode(account.get().getEncryptedPinCode());
            String enc = keyGeneratorService.decrypt(decryptDto);

            if(enc.equals(requestBodyChangePinCodeDto.getEncryptedPinCode())){
                DecryptDto dto=new DecryptDto();
                dto.setEncryptedPinCode(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               String ch= keyGeneratorService.encrypt(dto);
               account.get().setEncryptedPinCode(ch);
               transferAccountRepository.saveAndFlush(account.get());
               responseChangePinCodeDto.setStatus(HttpStatus.ACCEPTED);
               responseChangePinCodeDto.setCustomerType(customerType);
               responseChangePinCodeDto.setMsisdn(msisdn);
               responseChangePinCodeDto.setEncryptedNewPinCode(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               responseChangePinCodeDto.setEncryptedPinCode(keyGeneratorService.decrypt(new DecryptDto(account.get().getEncryptedPinCode())));
               return responseChangePinCodeDto;
            }
            else
            {
                throw new RuntimeException("Les deux pincodes ne correspondent pas");
            }
        }

    }

    @Override
    public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber, WalletTypeDto dto) {

        RequestBodyUserProfileDto userProfileDto = new RequestBodyUserProfileDto();
        AmountDto amountDto= new AmountDto();
        WalletType walletType= dto.getWalletType();
        Account account = transferAccountRepository.findByPhoneNumberAndWalletType(
                phoneNumber,walletType).orElseThrow(() ->
                new ResourceNotFoundException("msisdn invalid"));
        amountDto.setCurrency(TransactionCurrency.XOF);
        amountDto.setValue(account.getBalance());
        userProfileDto.setMsisdn(account.getPhoneNumber());
        userProfileDto.setBalance(amountDto);
        userProfileDto.setSuspended(account.is_active());
        if(account.getCustomer()!=null){
            userProfileDto.setType(String.valueOf(CustomerType.CUSTOMER));
            userProfileDto.setLastName(account.getCustomer().getLastName());
            userProfileDto.setFirstName(account.getCustomer().getFirstName());
        }
        else if(account.getRetailer()!=null){
            userProfileDto.setType(String.valueOf(CustomerType.RETAILER));
            userProfileDto.setLastName(account.getRetailer().getLastName());
            userProfileDto.setFirstName(account.getRetailer().getFirstName());
            userProfileDto.setUserId(account.getRetailer().getUserId());
        }
        return userProfileDto;
    }
}
