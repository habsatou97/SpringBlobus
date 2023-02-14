package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.ErrorCode;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
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
    public CreateOrEditAccountDto createRetailerTransfertAccount(CreateOrEditAccountDto transferAccount,Long id) {
        Account account = new Account();
        User retailer = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
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
                        && transferAccount.getWalletType()!=null && transferAccount.getEncryptedPinCode()!=null
                        && transferAccount.getEncryptedPinCode().length() >0){
                    account.setRetailer(retailer);
                    account.set_active(true);
                    account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                    account.setWalletType(transferAccount.getWalletType());
                    account.setPhoneNumber(transferAccount.getPhoneNumber());
                    account.setBalance(0.0);
                    Account compte = transferAccountRepository.save(account);
                    retailer.addTransferAccounts(compte);
                    return transferAccount;
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

                    if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() ==9
                            && transferAccount.getWalletType()!=null && transferAccount.getEncryptedPinCode()!=null
                            && transferAccount.getEncryptedPinCode().length() >0){
                        account.setRetailer(retailer);
                        account.set_active(true);
                        account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                        account.setWalletType(transferAccount.getWalletType());
                        account.setPhoneNumber(transferAccount.getPhoneNumber());
                        account.setBalance(0.0);
                        Account compte = transferAccountRepository.save(account);
                        retailer.addTransferAccounts(compte);
                        return transferAccount;

                    } throw new IllegalStateException("Veuillez renseignez les donnez correctement");

                } else throw new EntityNotFoundException("Retailer with id" + ": " + id + " don't exist");
            }
        }
    }

    @Override
    public CreateOrEditAccountDto createCustomerTransfertAccount(CreateOrEditAccountDto transferAccount, Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
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
            //System.out.println("Ce customer possede deja un compte de ce type");
            //return null;
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
                return transferAccount;
            }
            throw new IllegalStateException("Veuillez renseignez les donnez correctement");

        }
    }

    @Override
    public List <CreateOrEditAccountDto> getAllTransfertAccount() {

        return transferAccountRepository.findAll().stream().map( account -> {
            CreateOrEditAccountDto dto = new CreateOrEditAccountDto();
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
            CreateOrEditAccountDto dto = new CreateOrEditAccountDto();
            dto.setPhoneNumber(account.getPhoneNumber());
            dto.setWalletType(account.getWalletType());
            dto.setEncryptedPinCode(account.getEncryptedPinCode());
            dto.setBalance(account.getBalance());
            return dto;
        }).findAny();

    }

    @Override
    public Optional<CreateOrEditAccountDto> geTransferAccountByMsisdn(String msisdn) {
        return Optional.empty();
    }

    @Override
    public CreateOrEditAccountDto EnableTransfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        CreateOrEditAccountDto dto = new CreateOrEditAccountDto();
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
    public CreateOrEditAccountDto DiseableTranfertAccount(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        CreateOrEditAccountDto dto = new CreateOrEditAccountDto();

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
    public String GetAccountPhoneNumber(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent())
            return existingAccount.get().getPhoneNumber();
        else throw new EntityNotFoundException("Account with id"+": "+id+ " don't exist");
    }
    @Override
    public CreateOrEditAccountDto updateTranfertAccount(CreateOrEditAccountDto transferAccount, Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            if(transferAccount.getBalance()!=0)
            {
           existingAccount.get().setBalance(transferAccount.getBalance());
            }
            if (transferAccount.getWalletType()!=null){
                existingAccount.get().setWalletType(transferAccount.getWalletType());
            }
           if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() >0
                   && !Objects.equals(transferAccount.getPhoneNumber(),existingAccount.get().getPhoneNumber()))
           {
            existingAccount.get().setEncryptedPinCode(transferAccount.getEncryptedPinCode());
           }
           Account compte = transferAccountRepository.save(existingAccount.get());
               CreateOrEditAccountDto account = new CreateOrEditAccountDto();
               account.setBalance(compte.getBalance());
               account.setWalletType(compte.getWalletType());
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
    public double getBalance(GetRetailerBalanceDto getRetailerBalanceDto) {
        return transferAccountRepository.findByPhoneNumberAndWalletTypeAndEncryptedPinCode(
                getRetailerBalanceDto.getPhoneNumber(),
                getRetailerBalanceDto.getWalletType(),
                getRetailerBalanceDto.getEncryptedPinCode()).orElseThrow().getBalance();
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
                                                                CreateOrEditAccountDto account,
                                                                Role role) {
        Account account1 = transferAccountRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Account not found"));
        if(account1.getRetailer().getRoles().contains(Role.RETAILER)){
            account1.setBalance(account.getBalance());
            account1.setPhoneNumber(account.getPhoneNumber());
            account1.setWalletType(account1.getWalletType());
            account1.setEncryptedPinCode(account.getEncryptedPinCode());
             transferAccountRepository.saveAndFlush(account1);
             return account;
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
        throw new EntityNotFoundException("This account does'nt exist !!");
    }

    @Override
    public ResponseChangePinCodeDto changePinCode(RequestBodyChangePinCodeDto requestBodyChangePinCodeDto,
                                                  QueryParameterChangePinCodeDto queryParameterChangePinCodeDto)
            throws NoSuchPaddingException,
                 IllegalBlockSizeException,
                 IOException,
                 NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {

        KeyDto keyDto = new KeyDto();
        ResponseChangePinCodeDto responseChangePinCodeDto = new ResponseChangePinCodeDto();

        Optional<Account> account = transferAccountRepository.getAccountByPhoneNumber(queryParameterChangePinCodeDto.getMsisdn());
        if((queryParameterChangePinCodeDto.getCustomerType())!= CustomerType.RETAILER){
            throw new EntityNotFoundException("Only a retailer is allow to do this operation !!");
        }
        else if(!account.isPresent()) {
            responseChangePinCodeDto.setErrorCode("2000");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;
        } else if (queryParameterChangePinCodeDto.getMsisdn().length() != 9) {
            responseChangePinCodeDto.setErrorCode(ErrorCode.CUSTOMER_MSISDN_IS_INVALID.getErrorCode());
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (requestBodyChangePinCodeDto == null) {
            responseChangePinCodeDto.setErrorCode("21");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (requestBodyChangePinCodeDto.getEncryptedNewPinCode() == null ||
                requestBodyChangePinCodeDto.getEncryptedPinCode() == null) {
            responseChangePinCodeDto.setErrorCode("22");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;
        } else if (queryParameterChangePinCodeDto==null) {
            responseChangePinCodeDto.setErrorCode("27");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else {
            String enc = keyGeneratorService.decrypt(account.get().getEncryptedPinCode());

            if(enc.equals(requestBodyChangePinCodeDto.getEncryptedPinCode())){
               String ch= keyGeneratorService.encrypt(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               account.get().setEncryptedPinCode(ch);
               responseChangePinCodeDto.setStatus(HttpStatus.ACCEPTED);
               return responseChangePinCodeDto;


              // responseChangePinCodeDto.getEncryptedNewPinCode(requestBodyChangePinCodeDto.setEncryptedNewPinCode(requestBodyChangePinCodeDto.getEncryptedPinCode()));
            }
            else
            {
                throw new RuntimeException("Les deux pincodes ne correspondent pas");
            }
        }
            //return null;
    }
}
