package com.blobus.apiExterneBlobus.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blobus.apiExterneBlobus.dto.AmountDto;
import com.blobus.apiExterneBlobus.dto.CustomerDto;
import com.blobus.apiExterneBlobus.dto.GetTransactionDto;
import com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto;
import com.blobus.apiExterneBlobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiExterneBlobus.dto.RetailerDto;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Bulk;
import com.blobus.apiExterneBlobus.models.Transaction;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.BulkRepository;
import com.blobus.apiExterneBlobus.repositories.TransactionRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl;

import java.time.LocalDate;
import java.util.Optional;

import com.blobus.apiExterneBlobus.services.interfaces.KeyGeneratorService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class TransactionControllerTest {


    @Test
    void testCashInTransaction() {

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByPhoneNumberAndWalletType((String) any(), (WalletType) any()))
                .thenReturn(Optional.empty());
        TransactionController transactionController = new TransactionController(
                new TransactionServiceImpl(accountRepository,
                        mock(TransactionRepository.class), mock(BulkRepository.class),
                        mock(UserRepository.class),mock(KeyGeneratorService.class)));
        AmountDto amount = new AmountDto();
        CustomerDto customer = new CustomerDto("4105551212", WalletType.BONUS);

        ResponseEntity<ResponseCashInTransactionDto> actualCashInTransactionResult = transactionController
                .CashInTransaction(new RequestBodyTransactionDto(amount, customer,
                        new RetailerDto("4105551212",
                                "Encrypted Pin Code", WalletType.BONUS),
                        "Reference", true,
                        LocalDate.ofEpochDay(1L), TransactionType.CASHIN));
        assertTrue(actualCashInTransactionResult.hasBody());
        assertTrue(actualCashInTransactionResult.getHeaders().isEmpty());
        assertEquals(200, actualCashInTransactionResult.getStatusCodeValue());
        ResponseCashInTransactionDto body = actualCashInTransactionResult.getBody();
        assertNull(body.getTransactionId());
        assertEquals("2002", body.getErrorCode());
        assertNull(body.getDescription());
        assertNull(body.getBulkId());
        assertEquals(TransactionStatus.REJECTED, body.getStatus());
        assertNull(body.getReference());
        assertEquals("Retailer account does not exist", body.getErrorMessage());
        verify(accountRepository, atLeast(1))
                .findByPhoneNumberAndWalletType((String) any(), (WalletType) any());
    }

    @Test
    void testGetTransactionStatus() {
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(new Transaction()));
        assertNull(
                (new TransactionController(new TransactionServiceImpl(
                        mock(AccountRepository.class), transactionRepository,
                        mock(BulkRepository.class), mock(UserRepository.class),
                        mock(KeyGeneratorService.class))))
                        .getTransactionStatus(123L).getStatus());
        verify(transactionRepository).findById((Long) any());
    }

    @Test
    void testBulkCashInTransaction() {


        ResponseCashInTransactionDto responseCashInTransactionDto = new ResponseCashInTransactionDto();
        responseCashInTransactionDto.setBulkId(123L);
        responseCashInTransactionDto.setDescription("The characteristics of someone or something");
        responseCashInTransactionDto.setErrorCode("An error occurred");
        responseCashInTransactionDto.setErrorMessage("An error occurred");
        responseCashInTransactionDto.setReference("Reference");
        responseCashInTransactionDto.setStatus(TransactionStatus.ACCEPTED);
        responseCashInTransactionDto.setTransactionId(123L);
        TransactionServiceImpl transactionServiceImpl = mock(TransactionServiceImpl.class);
        when(transactionServiceImpl.BulkCashInTransaction((RequestBodyTransactionDto[]) any()))
                .thenReturn(responseCashInTransactionDto);
        TransactionController transactionController = new TransactionController(transactionServiceImpl);
        AmountDto amount = new AmountDto();
        CustomerDto customer = new CustomerDto("4105551212", WalletType.BONUS);

        ResponseEntity<ResponseCashInTransactionDto> actualBulkCashInTransactionResult = transactionController
                .BulkCashInTransaction(new RequestBodyTransactionDto[]{new RequestBodyTransactionDto(amount, customer,
                        new RetailerDto("4105551212", "Encrypted Pin Code", WalletType.BONUS), "Reference", true,
                        LocalDate.ofEpochDay(1L), TransactionType.CASHIN)});
        assertTrue(actualBulkCashInTransactionResult.hasBody());
        assertTrue(actualBulkCashInTransactionResult.getHeaders().isEmpty());
        assertEquals(200, actualBulkCashInTransactionResult.getStatusCodeValue());
        verify(transactionServiceImpl).BulkCashInTransaction((RequestBodyTransactionDto[]) any());
    }

    @Test
    void testGetTransaction() {
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        LocalDate createdDate = LocalDate.ofEpochDay(1L);
        LocalDate requestDate = LocalDate.ofEpochDay(1L);
        Account retailerTransferAccount = new Account();
        Account customerTransferAccount = new Account();
        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(
                new Transaction(123L, "Reference",
                createdDate, true, requestDate,
                        TransactionStatus.ACCEPTED, TransactionType.CASHIN, 10.0d,
                TransactionCurrency.XOF, retailerTransferAccount, customerTransferAccount, new Bulk())));
        GetTransactionDto actualTransaction = (new TransactionController(
                new TransactionServiceImpl(mock(AccountRepository.class),
                        transactionRepository, mock(BulkRepository.class),
                        mock(UserRepository.class),mock(KeyGeneratorService.class))))
                .getTransaction(123L);
        assertTrue(actualTransaction.isReceiveNotification());
        assertEquals("1970-01-02", actualTransaction.getCreatedAt().toString());
        assertNull(actualTransaction.getPartner());
        assertEquals(123L, actualTransaction.getTransactionId().longValue());
        assertEquals("CASHIN", actualTransaction.getType());
        assertEquals("ACCEPTED", actualTransaction.getStatus());
        assertEquals("1970-01-02", actualTransaction.getRequestDate().toString());
        assertEquals("Reference", actualTransaction.getReference());
        assertNull(actualTransaction.getCustomer());
        AmountDto amount = actualTransaction.getAmount();
        assertEquals(TransactionCurrency.XOF, amount.getCurrency());
        assertEquals(10.0d, amount.getValue().doubleValue());
        verify(transactionRepository).findById((Long) any());
    }
}

