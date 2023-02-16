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
import org.assertj.core.api.Assertions;
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
        CustomerDto customer = new CustomerDto("785462513", WalletType.BONUS);

        ResponseEntity<ResponseCashInTransactionDto> actualCashInTransactionResult =
                transactionController.CashInTransaction(new RequestBodyTransactionDto(amount, customer,
                        new RetailerDto("785462513",
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
        CustomerDto customer = new CustomerDto("785462513", WalletType.BONUS);

        ResponseEntity<ResponseCashInTransactionDto> actualBulkCashInTransactionResult =
                transactionController.BulkCashInTransaction(new RequestBodyTransactionDto[]{
                        new RequestBodyTransactionDto(amount, customer,
                        new RetailerDto(
                                "785462513",
                                "Encrypted Pin Code",
                                WalletType.BONUS),
                                "Reference",
                                true,
                                 LocalDate.ofEpochDay(1L),
                                TransactionType.CASHIN)});

        assertTrue(actualBulkCashInTransactionResult.hasBody());
        assertTrue(actualBulkCashInTransactionResult.getHeaders().isEmpty());
        assertEquals(200, actualBulkCashInTransactionResult.getStatusCodeValue());
        verify(transactionServiceImpl).BulkCashInTransaction((RequestBodyTransactionDto[]) any());
    }

    @Test
    void testGetTransaction() {
        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        TransactionServiceImpl service=mock(TransactionServiceImpl.class);
        TransactionController controller=mock(TransactionController.class);
        LocalDate createdDate = LocalDate.ofEpochDay(1L);
        LocalDate requestDate = LocalDate.ofEpochDay(1L);
        Account retailerTransferAccount = new Account();
        Account customerTransferAccount = new Account();

        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(
                new Transaction(
                        52L,
                        "Reference",
                         createdDate,
                        true,
                        requestDate,
                        TransactionStatus.ACCEPTED,
                        TransactionType.CASHIN,
                        10.0d,
                        TransactionCurrency.XOF,
                        retailerTransferAccount,
                        customerTransferAccount,
                        new Bulk())));
        GetTransactionDto actualTransaction = controller.getTransaction(52L);
        Assertions.assertThat(controller.getTransaction(52L)).isNull();
    }
}

