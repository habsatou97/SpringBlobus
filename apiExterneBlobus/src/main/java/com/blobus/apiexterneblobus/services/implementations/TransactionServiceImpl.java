package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.GetTransactionException;
import com.blobus.apiexterneblobus.models.Account;
import com.blobus.apiexterneblobus.models.Bulk;
import com.blobus.apiexterneblobus.models.Transaction;
import com.blobus.apiexterneblobus.repositories.BulkRepository;
import com.blobus.apiexterneblobus.repositories.TransactionRepository;
import com.blobus.apiexterneblobus.repositories.AccountRepository;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.interfaces.KeyGeneratorService;
import com.blobus.apiexterneblobus.services.interfaces.TransactionService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.blobus.apiexterneblobus.models.enums.ErrorCode.*;
import static com.blobus.apiexterneblobus.models.enums.TransactionStatus.*;

/*@Configuration
@EnableAsync
class AsynchConfiguration
{
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }
}*/

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository transferAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BulkRepository bulkRepository;
    private final UserRepository userRepository;
    private final KeyGeneratorService keyGeneratorService;
    Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
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
    //@Async("asyncExecutor")
    public void BulkCashInTransaction(HttpServletRequest request, RequestBodyTransactionDto[] requestBodyTransactionDtos) throws InterruptedException, JSONException {
        LOGGER.info("Debut de la transaction");
        BulkTransactionIsSuccess(request, requestBodyTransactionDtos);
        //return null;
    }

    /**
     * Cette methode retourne le status d'une transaction
     * @param transactionId
     * @return
     */
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

    /**
     * Cette methode retourne les information d'une transaction
     * @param transactionId
     * @return
     */
    @Override
    @Transactional
    public GetTransactionDto getTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        GetTransactionDto transactionDto= new GetTransactionDto();
        AmountDto amountDto= new AmountDto();
        UserDto userDto= new UserDto();
        CustomerEditCreateDto customerDto = new CustomerEditCreateDto();
        if (transaction.isPresent()){
            transactionDto.setTransactionId(transaction.get().getId());
            transactionDto.setCreatedAt(transaction.get().getCreatedDate());
            transactionDto.setReceiveNotification(transaction.get().getReceiveNotificatiion());
            transactionDto.setRequestDate(transaction.get().getRequestDate());
            transactionDto.setReference(transaction.get().getReference());
            transactionDto.setType(String.valueOf(transaction.get().getType()));
            transactionDto.setStatus(String.valueOf(transaction.get().getStatus()));

            userDto.setPhoneNumber(transaction.get().getRetailerTransferAccount().getRetailer().getPhoneNumber());
            userDto.setFirstName(transaction.get().getRetailerTransferAccount().getRetailer().getFirstName());
            userDto.setLastName(transaction.get().getRetailerTransferAccount().getRetailer().getLastName());
            userDto.setEmail(transaction.get().getRetailerTransferAccount().getRetailer().getEmail());
            transactionDto.setPartner(userDto);

            customerDto.setEmail(transaction.get().getCustomerTransferAccount().getCustomer().getEmail());
            customerDto.setFirstName(transaction.get().getCustomerTransferAccount().getCustomer().getFirstName());
            customerDto.setLastName(transaction.get().getCustomerTransferAccount().getCustomer().getLastName());
            customerDto.setPhoneNumber(transaction.get().getCustomerTransferAccount().getCustomer().getPhoneNumber());
            transactionDto.setCustomer(customerDto);

            amountDto.setValue(transaction.get().getAmount());
            amountDto.setCurrency(transaction.get().getCurrency());
            transactionDto.setAmount(amountDto);

            return transactionDto;
        }
       else
           throw new GetTransactionException("Transaction not allowed");
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
    private void BulkTransactionIsSuccess(HttpServletRequest request, RequestBodyTransactionDto[] requestBodyTransactionDtos) throws InterruptedException, JSONException {
        // pour recuperer le compte du retailer
        Optional<Account> retailerAccountBulk = transferAccountRepository
                .findByPhoneNumberAndWalletType(
                        requestBodyTransactionDtos[0].getRetailer().getPhoneNumber(),
                        requestBodyTransactionDtos[0].getRetailer().getWalletType()
                );
        List<ResponseCashInTransactionDto> responseCashInTransactionDtos = new ArrayList<>();

        if (retailerAccountBulk.isPresent()) {

            Bulk bulk = new Bulk(); // creation du Bulk du retailer
            bulk = bulkRepository.save(bulk);
            retailerAccountBulk.get().getRetailer().addBulks(bulk);
            userRepository.save(retailerAccountBulk.get().getRetailer());
            bulk.setRetailer(retailerAccountBulk.get().getRetailer());
            bulk = bulkRepository.save(bulk);

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


                if (codePinIsvalid(retailerAccount.get(), requestBodyTransactionDto.getRetailer().getEncryptedPinCode())) {
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
                            transaction.setStatus(SUCCESS);
                            transactionSave = transactionRepository.save(transactionSave);
                            transactionRepository.save(transaction);

                            bulk.addTransactions(transaction);
                            transaction.setBulk(bulk);
                            bulkRepository.save(bulk);
                            transactionRepository.save(transaction);

                            ResponseCashInTransactionDto responseCashInTransactionDto =
                                    ResponseCashInTransactionDto
                                            .builder()
                                            .transactionId(transaction.getId())
                                            .status(SUCCESS)
                                            .reference(transaction.getReference())
                                            .customerPhoneNumber(customerAccount.get().getPhoneNumber())
                                            .build();
                            responseCashInTransactionDtos.add(responseCashInTransactionDto);

                        } else {
                            responseCashInTransactionDtos.add(
                                    ResponseCashInTransactionDto
                                            .builder()
                                            .reference(requestBodyTransactionDto.getReference())
                                            .status(FAILED)
                                            .errorCode(BALANCE_INSUFFICIENT.getErrorCode())
                                            .errorMessage(BALANCE_INSUFFICIENT.getErrorMessage())
                                            .customerPhoneNumber(requestBodyTransactionDto.getCustomer().getPhoneNumber())
                                            .build()
                            );
                        }
                    } else {
                        ResponseCashInTransactionDto responseCashInTransactionDto =
                                ResponseCashInTransactionDto
                                        .builder()
                                        .reference(requestBodyTransactionDto.getReference())
                                        .status(REJECTED)
                                        .errorCode(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                                        .errorMessage(CUSTOMER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                                        .customerPhoneNumber(requestBodyTransactionDto.getCustomer().getPhoneNumber())
                                        .build();
                        responseCashInTransactionDtos.add(responseCashInTransactionDto);
                    }
                } else {
                    responseCashInTransactionDtos.add(
                            ResponseCashInTransactionDto
                                    .builder()
                                    .reference(requestBodyTransactionDto.getReference())
                                    .status(FAILED)
                                    .errorCode(INVALID_PIN_CODE.getErrorCode())
                                    .errorMessage(INVALID_PIN_CODE.getErrorMessage())
                                    .build()
                    );
                    break;
                }
            }
        }else {
            responseCashInTransactionDtos.add(
                    ResponseCashInTransactionDto
                            .builder()
                            .status(REJECTED)
                            .errorCode(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorCode())
                            .errorMessage(RETAILER_ACCOUNT_DOES_NOT_EXIST.getErrorMessage())
                            .build()
            );
        }

        String xCallbackUrl = request.getHeader("x-callback-url");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject JsonObject = new JSONObject();
        String jsonList = new Gson().toJson(responseCashInTransactionDtos);

        HttpEntity<String> requestHttp = new HttpEntity<String>(jsonList.toString(), headers);

        String responseEntity = restTemplate.postForObject(xCallbackUrl, requestHttp, String.class);
        //LOGGER.info("x-callback-url: "+ xCallbackUrl);
        //LOGGER.info("Fin de la transaction");
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
