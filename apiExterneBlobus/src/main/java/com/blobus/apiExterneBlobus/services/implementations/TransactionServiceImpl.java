package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.*;
import com.blobus.apiExterneBlobus.exception.GetTransactionException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Transaction;
import com.blobus.apiExterneBlobus.repositories.TransactionRepository;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.blobus.apiExterneBlobus.models.enums.TransactionStatus.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository transferAccountRepository;
    private final TransactionRepository transactionRepository;
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
        transaction.setCreatedDate(Date.from(Instant.now()));
        transaction.setType(requestBodyTransactionDto.getTransactionType());

        return transaction;
    }

    @Override
    public ResponseCashInTransactionDto CashInTransaction(RequestBodyTransactionDto requestBodyTransactionDto) {
        return TransactionIsSuccess(requestBodyTransactionDto);
    }

    @Override
    @Transactional
    public TransactionDto getTransactionStatus(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        TransactionDto dto= new TransactionDto();
        if(transaction.isPresent()){
            dto.setStatus(transaction.get().getStatus());
            return dto;
        }
        return null;
    }

    @Override
    @Transactional
    public GetTransactionDto getTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        GetTransactionDto transactionDto= new GetTransactionDto();
        AmountDto amountDto= new AmountDto();
        if (transaction.isPresent()){
            transactionDto.setTransactionId(transaction.get().getId());
            transactionDto.setCreatedAt(transaction.get().getCreatedDate());
            transactionDto.setReceiveNotification(transaction.get().getReceiveNotificatiion());
            transactionDto.setRequestDate(transaction.get().getRequestDate());
            transactionDto.setReference(transaction.get().getReference());
            transactionDto.setType(String.valueOf(transaction.get().getType()));
            transactionDto.setStatus(String.valueOf(transaction.get().getStatus()));
            transactionDto.setCustomer(transaction.get().getCustomerTransferAccount().getCustomer());
            transactionDto.setPartner(transaction.get().getRetailerTransferAccount().getRetailer());
            amountDto.setValue(transaction.get().getAmount());
            amountDto.setCurrency(transaction.get().getCurrency());
            transactionDto.setAmount(amountDto);
            return transactionDto;
        }
       return null;
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
                                .errorCode("2020")
                                .errorMessage("balance insufficient")
                                .build();
                    }
                }else {
                    return ResponseCashInTransactionDto
                            .builder()
                            .status(REJECTED)
                            .errorCode("2000")
                            .errorMessage("customer account does not exist")
                            .build();
                }
            }else {
                return ResponseCashInTransactionDto
                        .builder()
                        .status(FAILED)
                        .errorCode("2012")
                        .errorMessage("invalid Code Pin")
                        .build();
            }
        }else {
            return ResponseCashInTransactionDto
                    .builder()
                    .status(REJECTED)
                    .errorCode("2000")
                    .errorMessage("retailer account does not exist")
                    .build();
        }
    }

    //pour verifier si le solde du compte est suffisant
    private boolean balanceIsSufficient(Account account,double montantASoustraire){
        return account.getBalance() - montantASoustraire > 0 ? true : false;
    }
    // pour verifier si le codePin est valide
    private boolean codePinIsvalid(Account account,String codePin){
        return account.getEncryptedPinCode().equals(codePin) ? true : false;
    }
    // pour verifier si le numero de telephone du retailer ou du customer est valide
    private boolean phoneNumberIsvalid(Account account,String phoneNumber){
        return account.getPhoneNumber().equals(phoneNumber) ? true : false;
    }
}
