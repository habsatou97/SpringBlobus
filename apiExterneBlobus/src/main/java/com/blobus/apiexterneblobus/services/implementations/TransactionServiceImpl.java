package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.GetTransactionException;
import com.blobus.apiexterneblobus.exception.ResourceNotFoundException;
import com.blobus.apiexterneblobus.models.Account;
import com.blobus.apiexterneblobus.models.Bulk;
import com.blobus.apiexterneblobus.models.Transaction;
import com.blobus.apiexterneblobus.repositories.BulkRepository;
import com.blobus.apiexterneblobus.repositories.TransactionRepository;
import com.blobus.apiexterneblobus.repositories.AccountRepository;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.interfaces.KeyGeneratorService;
import com.blobus.apiexterneblobus.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static com.blobus.apiexterneblobus.models.enums.ErrorCode.*;
import static com.blobus.apiexterneblobus.models.enums.TransactionStatus.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository transferAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BulkRepository bulkRepository;
    private final UserRepository userRepository;
    private final KeyGeneratorService keyGeneratorService;
    @Override
    public Transaction convertDtoToEntityTransaction(RequestBodyTransactionDto requestBodyTransactionDto) {
       Optional<Account> retailerAccount = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDto.getRetailer().getPhoneNumber(),
                        requestBodyTransactionDto.getRetailer().getWalletType()
                );
        Optional<Account> customerAccount = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDto.getCustomer().getPhoneNumber(),
                        requestBodyTransactionDto.getCustomer().getWalletType()
                );
        Transaction transaction = new Transaction();
        transaction.setCustomerTransferAccount(customerAccount.get());
        transaction.setRetailerTransferAccount(retailerAccount.get());
        transaction.setType(requestBodyTransactionDto.getTransactionType());
        transaction.setReference(requestBodyTransactionDto.getReference());
        transaction.setAmount(requestBodyTransactionDto.getAmount().getValue());
        transaction.setCurrency(requestBodyTransactionDto.getAmount().getCurrency());
        transaction.setReceiveNotificatiion(requestBodyTransactionDto.getReceiveNotificatiion());
        transaction.setRequestDate(requestBodyTransactionDto.getRequestDate());
        transaction.setCreatedDate(LocalDate.now());
        transaction.setType(requestBodyTransactionDto.getTransactionType());

        return transaction;
    }

    @Override
    public ResponseCashInTransactionDto CashInTransaction(RequestBodyTransactionDto requestBodyTransactionDto) {
        return TransactionIsSuccess(requestBodyTransactionDto);
    }
    @Override
    public ResponseCashInTransactionDto BulkCashInTransaction(RequestBodyTransactionDto[] requestBodyTransactionDtos){
        // pour calculer le montant total a envoyer aux customer
        double montantTotalAEnvoyerAuxCustomer = Arrays.stream(requestBodyTransactionDtos)
                .map(requestBodyTransactionDto1 -> requestBodyTransactionDto1.getAmount().getValue())
                .reduce(0.0,Double::sum);

        // pour recuperer le compte du retailer
        Optional<Account> retailerAccount = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDtos[0].getRetailer().getPhoneNumber(),
                        requestBodyTransactionDtos[0].getRetailer().getWalletType()
                );

        if (retailerAccount.isPresent()){
            if (balanceIsSufficient(retailerAccount.get(),montantTotalAEnvoyerAuxCustomer)){
                Bulk bulk = new Bulk(); // creation du Bulk du retailer
                bulk = bulkRepository.save(bulk);
                retailerAccount.get().getRetailer().addBulks(bulk);
                userRepository.save(retailerAccount.get().getRetailer());
                bulk.setRetailer(retailerAccount.get().getRetailer());
                bulk = bulkRepository.save(bulk);
                return BulkTransactionIsSuccess(requestBodyTransactionDtos,bulk);
            }else {
                return ResponseCashInTransactionDto
                        .builder()
                        .status(FAILED)
                        .errorCode(BALANCE_INSUFFICIENT.getErrorCode())
                        .errorMessage(BALANCE_INSUFFICIENT.getErrorMessage())
                        .build();
            }
        }else {
            return ResponseCashInTransactionDto
                    .builder()
                    .status(REJECTED)
                    .errorCode(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                    .errorMessage(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                    .build();
        }
    }

    /**
     * Cette methode retourne le status d'une transaction
     * @param transactionId
     * @return
     */
    @Override
    @Transactional
    public TransactionDto getTransactionStatus(Long transactionId) {
        Transaction transaction = transactionRepository.findById(
                transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction "+transactionId+" not found"));
        TransactionDto dto= new TransactionDto();
            dto.setStatus(transaction.getStatus());
            return dto;
    }

    /**
     * Cette methode retourne les information d'une transaction
     * @param transactionId
     * @return
     */
    @Override
    @Transactional
    public GetTransactionDto getTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(
                transactionId).orElseThrow(() -> new ResourceNotFoundException("transaction "+transactionId+" not found"));
        GetTransactionDto transactionDto= new GetTransactionDto();
        AmountDto amountDto= new AmountDto();
        UserDto userDto= new UserDto();
        CustomerEditCreateDto customerDto = new CustomerEditCreateDto();
            transactionDto.setTransactionId(transaction.getId());
            transactionDto.setCreatedAt(transaction.getCreatedDate());
            transactionDto.setReceiveNotification(transaction.getReceiveNotificatiion());
            transactionDto.setRequestDate(transaction.getRequestDate());
            transactionDto.setReference(transaction.getReference());
            transactionDto.setType(String.valueOf(transaction.getType()));
            transactionDto.setStatus(String.valueOf(transaction.getStatus()));

            userDto.setPhoneNumber(transaction.getRetailerTransferAccount().getRetailer().getPhoneNumber());
            userDto.setFirstName(transaction.getRetailerTransferAccount().getRetailer().getFirstName());
            userDto.setLastName(transaction.getRetailerTransferAccount().getRetailer().getLastName());
            userDto.setEmail(transaction.getRetailerTransferAccount().getRetailer().getEmail());
            transactionDto.setPartner(userDto);

            customerDto.setEmail(transaction.getCustomerTransferAccount().getCustomer().getEmail());
            customerDto.setFirstName(transaction.getCustomerTransferAccount().getCustomer().getFirstName());
            customerDto.setLastName(transaction.getCustomerTransferAccount().getCustomer().getLastName());
            customerDto.setPhoneNumber(transaction.getCustomerTransferAccount().getCustomer().getPhoneNumber());
            transactionDto.setCustomer(customerDto);

            amountDto.setValue(transaction.getAmount());
            amountDto.setCurrency(transaction.getCurrency());
            transactionDto.setAmount(amountDto);

            return transactionDto;


    }

    @Transactional
    // pour effectuer une transaction CashIn (operation intermediaire)
    private ResponseCashInTransactionDto TransactionIsSuccess(RequestBodyTransactionDto requestBodyTransactionDto){
        Optional<Account> retailerAccount = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDto.getRetailer().getPhoneNumber(),
                        requestBodyTransactionDto.getRetailer().getWalletType()
                );
        Optional<Account> customerAccount = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDto.getCustomer().getPhoneNumber(),
                        requestBodyTransactionDto.getCustomer().getWalletType()
                );

        if (retailerAccount.isPresent()){
            if (codePinIsvalid(retailerAccount.get(),requestBodyTransactionDto.getRetailer().getEncryptedPinCode())){
                if (customerAccount.isPresent()){
                    Transaction transaction = convertDtoToEntityTransaction(requestBodyTransactionDto);
                    if (balanceIsSufficient(transaction.getRetailerTransferAccount(),transaction.getAmount())){
                        transaction.getRetailerTransferAccount().setBalance(transaction.getRetailerTransferAccount().getBalance()-transaction.getAmount());
                        transaction.getCustomerTransferAccount().setBalance(transaction.getCustomerTransferAccount().getBalance()+transaction.getAmount());
                        transferAccountRepository.save(transaction.getRetailerTransferAccount());
                        transferAccountRepository.save(transaction.getCustomerTransferAccount());
                        Transaction transactionSave = transactionRepository.save(transaction);
                        transaction.getRetailerTransferAccount().getRetailerTransactions().add(transactionSave);
                        transaction.getCustomerTransferAccount().getCustomerTransactions().add(transactionSave);
                        transferAccountRepository.save(transaction.getRetailerTransferAccount());
                        transferAccountRepository.save(transaction.getCustomerTransferAccount());
                        transaction.setStatus(SUCCESS);
                        transactionSave = transactionRepository.save(transactionSave);
                        transactionRepository.save(transaction);
                        return  ResponseCashInTransactionDto
                                .builder()
                                .status(SUCCESS)
                                .transactionId(transactionSave.getId())
                                .build();
                    }else {
                        return ResponseCashInTransactionDto
                                .builder()
                                .status(FAILED)
                                .errorCode(BALANCE_INSUFFICIENT.getErrorCode())
                                .errorMessage(BALANCE_INSUFFICIENT.getErrorMessage())
                                .build();
                    }
                }else {
                    return ResponseCashInTransactionDto
                            .builder()
                            .status(REJECTED)
                            .errorCode(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                            .errorMessage(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                            .build();
                }
            }else {
                return ResponseCashInTransactionDto
                        .builder()
                        .status(FAILED)
                        .errorCode(INVALID_PIN_CODE.getErrorCode())
                        .errorMessage(INVALID_PIN_CODE.getErrorMessage())
                        .build();
            }
        }else {
            return ResponseCashInTransactionDto
                    .builder()
                    .status(REJECTED)
                    .errorCode(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                    .errorMessage(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                    .build();
        }
    }

    @Transactional
    // pour effectuer une transaction BulkCashIn (operation intermediaire)
    private ResponseCashInTransactionDto BulkTransactionIsSuccess(RequestBodyTransactionDto[] requestBodyTransactionDtos, Bulk bulk){
        for (RequestBodyTransactionDto requestBodyTransactionDto : requestBodyTransactionDtos) {
            Optional<Account> retailerAccount = transferAccountRepository
                    .findByPhoneNumberAndWalletType(
                            requestBodyTransactionDto.getRetailer().getPhoneNumber(),
                            requestBodyTransactionDto.getRetailer().getWalletType()
                    );
            Optional<Account> customerAccount = transferAccountRepository
                    .findByPhoneNumberAndWalletType(
                            requestBodyTransactionDto.getCustomer().getPhoneNumber(),
                            requestBodyTransactionDto.getCustomer().getWalletType()
                    );

            if (retailerAccount.isPresent()) {
                if (codePinIsvalid(retailerAccount.get(),requestBodyTransactionDto.getRetailer().getEncryptedPinCode())) {
                    if (customerAccount.isPresent()) {
                        Transaction transaction = convertDtoToEntityTransaction(requestBodyTransactionDto);
                        if (balanceIsSufficient(transaction.getRetailerTransferAccount(), transaction.getAmount())) {
                            transaction.getRetailerTransferAccount().setBalance(transaction.getRetailerTransferAccount().getBalance() - transaction.getAmount());
                            transaction.getCustomerTransferAccount().setBalance(transaction.getCustomerTransferAccount().getBalance() + transaction.getAmount());
                            transferAccountRepository.save(transaction.getRetailerTransferAccount());
                            transferAccountRepository.save(transaction.getCustomerTransferAccount());
                            Transaction transactionSave = transactionRepository.save(transaction);
                            transaction.getRetailerTransferAccount().getRetailerTransactions().add(transactionSave);
                            transaction.getCustomerTransferAccount().getCustomerTransactions().add(transactionSave);
                            transferAccountRepository.save(transaction.getRetailerTransferAccount());
                            transferAccountRepository.save(transaction.getCustomerTransferAccount());
                            transaction.setStatus(TERMINATED);
                            transactionSave = transactionRepository.save(transactionSave);
                            transactionRepository.save(transaction);

                            bulk.addTransactions(transaction);
                            transaction.setBulk(bulk);
                            bulkRepository.save(bulk);
                            transactionRepository.save(transaction);

                        } else {
                            return ResponseCashInTransactionDto
                                    .builder()
                                    .status(FAILED)
                                    .errorCode(BALANCE_INSUFFICIENT.getErrorCode())
                                    .errorMessage(BALANCE_INSUFFICIENT.getErrorMessage())
                                    .build();
                        }
                    } else {
                        return ResponseCashInTransactionDto
                                .builder()
                                .status(REJECTED)
                                .errorCode(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                                .errorMessage(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                                .build();
                    }
                } else {
                    return ResponseCashInTransactionDto
                            .builder()
                            .status(FAILED)
                            .errorCode(INVALID_PIN_CODE.getErrorCode())
                            .errorMessage(INVALID_PIN_CODE.getErrorMessage())
                            .build();
                }
            } else {
                return ResponseCashInTransactionDto
                        .builder()
                        .status(REJECTED)
                        .errorCode(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                        .errorMessage(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                        .build();
            }
        }
        // le retour si tout se passe bien
        return ResponseCashInTransactionDto
                .builder()
                .status(TERMINATED)
                .bulkId(bulk.getBulkId())
                .build();
    }

    //pour verifier si le solde du compte est suffisant
    private boolean balanceIsSufficient(Account account,double montantASoustraire){
        return account.getBalance() - montantASoustraire >= 0 ? true : false;
    }
    // pour verifier si le codePin est valide
    private boolean codePinIsvalid(Account account,String encryptedPinCode){
        try {
            String ch1 = keyGeneratorService.decrypt(new DecryptDto(account.getEncryptedPinCode()));
            String ch2 = keyGeneratorService.decrypt(new DecryptDto(encryptedPinCode));
            return ch1.equals(ch2) ? true : false;
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    // pour verifier si le numero de telephone du retailer ou du customer est valide
    private boolean phoneNumberIsvalid(Account account,String phoneNumber){
        return account.getPhoneNumber().equals(phoneNumber) ? true : false;
    }
}
