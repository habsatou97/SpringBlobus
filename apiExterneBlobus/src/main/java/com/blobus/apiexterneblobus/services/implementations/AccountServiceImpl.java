package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.ChangePinCodeException;
import com.blobus.apiexterneblobus.exception.ResourceNotFoundException;
import com.blobus.apiexterneblobus.models.Account;
import com.blobus.apiexterneblobus.models.Customer;
import com.blobus.apiexterneblobus.models.User;
import com.blobus.apiexterneblobus.models.enums.*;
import com.blobus.apiexterneblobus.repositories.CustomerRepository;
import com.blobus.apiexterneblobus.repositories.AccountRepository;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.interfaces.AccountService;
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

    /**
     * Cette methode permet à l'administrteur d'ajouter un compte de tranfert pour un retailer
     * @param transferAccount
     * @param id
     * @return
     */
    @Override
    public CreateOrEditAccountDto createRetailerTransfertAccount(CreateAccountDto transferAccount,Long id) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
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
                    DecryptDto decryptDto=new DecryptDto();
                    decryptDto.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
                    account.setEncryptedPinCode(keyGeneratorService.encrypt(decryptDto));
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


            } else throw new ResourceNotFoundException("Retailer with id" + ": " + id + " don't exist");

        }
        // Si le retailer a deja au moins un compte :
        else {

            for (Account cpt : comptes ) {
                WalletType type = cpt.getWalletType();
                if (type.equals(transferAccount.getWalletType()))
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

                } else throw new ResourceNotFoundException("Retailer with id" + ": " + id + " don't exist");
            }
        }
    }

    /**
     * Cette methode permet à l'administrteur d'ajouter un compte de tranfert pour un customer
     * @param transferAccount
     * @param id
     * @return
     */
    @Override
    public CreateOrEditAccountDto createCustomerTransfertAccount(CreateAccountDto transferAccount, Long id) {

        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Customer not found"));
        Account account= new Account();


        if (customer.getTransferAccounts() != null){
            List<Account> comptes =   customer.getTransferAccounts();
            int i = 0;
            for (Account cpt : comptes
            ) {
                WalletType type = cpt.getWalletType();
                if(type.equals(transferAccount.getWalletType()))
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
        }else{
            account.setCustomer(customer);
            account.set_active(true);
            account.setEncryptedPinCode(transferAccount.getEncryptedPinCode());
            account.setWalletType(transferAccount.getWalletType());
            account.setPhoneNumber(transferAccount.getPhoneNumber());
            account.setBalance(0.0);
            Account compte = transferAccountRepository.save(account);
            CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                    .balance(account.getBalance())
                    .encryptedPinCode(transferAccount.getEncryptedPinCode())
                    .phoneNumber(transferAccount.getPhoneNumber())
                    .walletType(transferAccount.getWalletType())
                    .build();
            return dto;
        }

    }

    /**
     * Cette methode permet de visualiser l'ensemble des comptes de transferts existante
     * @return
     */
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

    /**
     * Cette methode permet de visualiser les informations d'un compte de transfert
     * @param id
     * @return
     */
    @Override
    public Optional<CreateOrEditAccountDto> getTransfertAccountById(Long id) {
        Optional<Account> accountDto = transferAccountRepository.findById(id);
        if (!accountDto.isPresent()){
            throw new ResourceNotFoundException("Account with id "+id+" don't exists.");
        }
        return accountDto.stream().map(account -> {
            CreateOrEditAccountDto dto =  CreateOrEditAccountDto.builder().build();
            dto.setPhoneNumber(account.getPhoneNumber());
            dto.setWalletType(account.getWalletType());
            dto.setEncryptedPinCode(account.getEncryptedPinCode());
            dto.setBalance(account.getBalance());
            return dto;
        }).findAny();

    }

    /**
     * Cette methode permet à l'administrateur d'activer un compte de transfert
     * @param id
     * @return
     */
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
        else throw new ResourceNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    /**
     * Cette methode permet à l'administrateur de désactiver un compte de transfert
     * @param id
     * @return
     */
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
        else throw new ResourceNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    /**
     * Cette methode permet  d'afficher le Msisdn  d'un compte de transfert
     * @param id
     * @return
     */
    @Override
    public String getAccountPhoneNumber(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent())
            return existingAccount.get().getPhoneNumber();
        else throw new ResourceNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    /**
     * Cette methode permet de modifer les informations d'un compte de transfert
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

           if(transferAccount.getPhoneNumber()!=null && transferAccount.getPhoneNumber().length() ==9
                   && !Objects.equals(transferAccount.getPhoneNumber(),existingAccount.get().getPhoneNumber()))
           {
            existingAccount.get().setPhoneNumber(transferAccount.getPhoneNumber());
           }
           if(transferAccount.getEncryptedPinCode()!=null && transferAccount.getEncryptedPinCode().length()>0)
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
        else throw new ResourceNotFoundException("Account with id "+id+ " don't exist");
        //return null;
    }

    /**
     * Cette methode permet de supprimer un compte de tranfert
     * @param id
     */
    @Override
    public void deleteTransfertAccountById(Long id) {
        Optional<Account> existingAccount = transferAccountRepository.findById(id);
        if (existingAccount.isPresent()) {
            transferAccountRepository.deleteById(id);
        }
        else throw new ResourceNotFoundException("Account with id"+": "+id+ " don't exist");
    }

    /**
     * Cette methode permet de visualiser le solde d'un compte de transfert
     * @param getRetailerBalanceDto
     * @return
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     */
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
     * Cette methode permet à l'administrateur de mettre à jour le compte de tranfert d'un retailer
     * @param id
     * @param account
     * @return
     */
    @Override
    public CreateOrEditAccountDto modifyTransferAccountRetailer(Long id, EditAccountDto account
                                                                ) {

        Account account1 = transferAccountRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Account not found"));


        if(account1.getRetailer().getRoles().contains(Role.RETAILER)){
            if(account.getPhoneNumber()!=null && account.getPhoneNumber().length() ==9)
            {
                account1.setPhoneNumber(account.getPhoneNumber());
            }
            if (!Objects.equals(account.getBalance(), account1.getBalance()) && account.getBalance()!=0)
            {
                account1.setBalance(account.getBalance());
            }
            if(account.getEncryptedPinCode()!=null && account.getEncryptedPinCode().length()>0
             && !Objects.equals(account.getEncryptedPinCode(),account1.getEncryptedPinCode()))
            {
                account1.setEncryptedPinCode(account.getEncryptedPinCode());
            }

            Account account2= transferAccountRepository.saveAndFlush(account1);
            CreateOrEditAccountDto dto = CreateOrEditAccountDto.builder()
                    .walletType(account1.getWalletType())
                    .balance(account2.getBalance())
                    .encryptedPinCode(account2.getEncryptedPinCode())
                    .phoneNumber(account2.getPhoneNumber())
                    .build();
             return dto;
        }
       throw new ResourceNotFoundException("This user isn't a retailer");
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
        throw new ResourceNotFoundException("This account doesn't exist !!");
    }

    /**
     * Cette methode permet de modifier le code pin d'un compte de transfert
     * @param requestBodyChangePinCodeDto
     * @param msisdn
     * @param customerType
     * @param walletType
     * @param content_type
     * @return
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    @Override
    public ResponseChangePinCodeDto changePinCode(RequestBodyChangePinCodeDto requestBodyChangePinCodeDto, String msisdn, CustomerType customerType, WalletType walletType,String content_type)
            throws NoSuchPaddingException,
            IllegalBlockSizeException,
            IOException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, ChangePinCodeException {

        KeyDto keyDto = new KeyDto();
        KeyDto keyDto1=new KeyDto();
        DecryptDto decryptDto=new DecryptDto();
        ResponseChangePinCodeDto responseChangePinCodeDto = new ResponseChangePinCodeDto();

        Optional<Account> account = transferAccountRepository.findByPhoneNumberAndWalletType(msisdn,walletType);
        if(customerType!= CustomerType.RETAILER){
            throw new ChangePinCodeException("Only a retailer is allow to do this operation !!");
        }
        else if(!account.isPresent()) {
            responseChangePinCodeDto.setErrorCode("2000");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;
        } else if (msisdn.length() != 9) {
            responseChangePinCodeDto.setErrorCode(ErrorCode.CUSTOMER_MSISDN_IS_INVALID.getErrorCode());
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            //return responseChangePinCodeDto;
            throw new ChangePinCodeException("A valid msisdn must have 9 caracters !!");

        } else if (requestBodyChangePinCodeDto == null) {
            responseChangePinCodeDto.setErrorCode("21");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            return responseChangePinCodeDto;

        } else if (content_type==null) {
            responseChangePinCodeDto.setErrorCode("25");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            responseChangePinCodeDto.setErrorMessage("This request needs headers");
           // return responseChangePinCodeDto;
            throw new ChangePinCodeException("This request needs headers");


        } else if (requestBodyChangePinCodeDto.getEncryptedNewPinCode() == null ||
                requestBodyChangePinCodeDto.getEncryptedPinCode() == null) {
            responseChangePinCodeDto.setErrorCode("22");
            responseChangePinCodeDto.setErrorMessage("You must enter the new encryptedPinCode");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            throw new ChangePinCodeException("You must enter the new encryptedPinCode");

            // return responseChangePinCodeDto;
        } else if (msisdn == null || customerType == null || walletType == null) {
            responseChangePinCodeDto.setErrorCode("27");
            responseChangePinCodeDto.setErrorMessage("CustomerType,WalletType or msisdn cannot be null");
            responseChangePinCodeDto.setStatus(HttpStatus.BAD_REQUEST);
            throw new ChangePinCodeException("CustomerType,WalletType or msisdn cannot be null");

            // return responseChangePinCodeDto;

        } else {

            decryptDto.setEncryptedPinCode(account.get().getEncryptedPinCode());
            String enc = keyGeneratorService.decrypt(decryptDto);
            DecryptDto dec=new DecryptDto();
            dec.setEncryptedPinCode(requestBodyChangePinCodeDto.getEncryptedPinCode());

            if(enc.equals(keyGeneratorService.decrypt(dec))){
                DecryptDto dto=new DecryptDto();
                dto.setEncryptedPinCode(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               account.get().setEncryptedPinCode(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               transferAccountRepository.saveAndFlush(account.get());
               responseChangePinCodeDto.setStatus(HttpStatus.ACCEPTED);
               responseChangePinCodeDto.setCustomerType(customerType);
               responseChangePinCodeDto.setMsisdn(msisdn);
               responseChangePinCodeDto.setEncryptedNewPinCode(requestBodyChangePinCodeDto.getEncryptedNewPinCode());
               responseChangePinCodeDto.setEncryptedPinCode(account.get().getEncryptedPinCode());
               return responseChangePinCodeDto;
            }
            else
            {
                throw new ChangePinCodeException("Les deux pincodes ne correspondent pas");
            }
        }

    }

    /**
     * Cette methode permet à un retailer de visualiser son profil à partir de son Msisdn
     * @param phoneNumber
     * @param walletType
     * @return
     */
    @Override
    public RequestBodyUserProfileDto getUserProfileByMsisdn(String phoneNumber, WalletType walletType) {

        RequestBodyUserProfileDto userProfileDto = new RequestBodyUserProfileDto();
        AmountDto amountDto= new AmountDto();

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
