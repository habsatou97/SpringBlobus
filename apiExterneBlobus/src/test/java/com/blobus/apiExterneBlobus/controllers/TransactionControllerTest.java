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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class TransactionControllerTest {
    /**
     * Method under test: {@link TransactionController#CashInTransaction(RequestBodyTransactionDto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCashInTransaction() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto["requestDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because "str" is null
        //       at com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl.codePinIsvalid(TransactionServiceImpl.java:299)
        //       at com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl.TransactionIsSuccess(TransactionServiceImpl.java:159)
        //       at com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl.CashInTransaction(TransactionServiceImpl.java:64)
        //       at com.blobus.apiExterneBlobus.controllers.TransactionController.CashInTransaction(TransactionController.java:20)
        //   See https://diff.blue/R013 to resolve this issue.

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByPhoneNumberAndWalletType((String) any(), (WalletType) any()))
                .thenReturn(Optional.of(new Account()));
        TransactionController transactionController = new TransactionController(new TransactionServiceImpl(
                accountRepository, mock(TransactionRepository.class), mock(BulkRepository.class), mock(UserRepository.class)));
        AmountDto amount = new AmountDto();
        CustomerDto customer = new CustomerDto("4105551212", WalletType.BONUS);

        transactionController.CashInTransaction(new RequestBodyTransactionDto(amount, customer,
                new RetailerDto("4105551212", "Encrypted Pin Code", WalletType.BONUS), "Reference", true,
                LocalDate.ofEpochDay(1L), TransactionType.CASHIN));
    }

    /**
     * Method under test: {@link TransactionController#CashInTransaction(RequestBodyTransactionDto)}
     */
    @Test
    void testCashInTransaction2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto["requestDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.findByPhoneNumberAndWalletType((String) any(), (WalletType) any()))
                .thenReturn(Optional.empty());
        TransactionController transactionController = new TransactionController(
                new TransactionServiceImpl(accountRepository, mock(TransactionRepository.class), mock(BulkRepository.class),
                        mock(UserRepository.class)));
        AmountDto amount = new AmountDto();
        CustomerDto customer = new CustomerDto("4105551212", WalletType.BONUS);

        ResponseEntity<ResponseCashInTransactionDto> actualCashInTransactionResult = transactionController
                .CashInTransaction(new RequestBodyTransactionDto(amount, customer,
                        new RetailerDto("4105551212", "Encrypted Pin Code", WalletType.BONUS), "Reference", true,
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
        verify(accountRepository, atLeast(1)).findByPhoneNumberAndWalletType((String) any(), (WalletType) any());
    }

    /**
     * Method under test: {@link TransactionController#getTransactionStatus(Long)}
     */
    @Test
    void testGetTransactionStatus() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(new Transaction()));
        assertNull(
                (new TransactionController(new TransactionServiceImpl(mock(AccountRepository.class), transactionRepository,
                        mock(BulkRepository.class), mock(UserRepository.class)))).getTransactionStatus(123L).getStatus());
        verify(transactionRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link TransactionController#getTransactionStatus(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTransactionStatus2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.services.interfaces.TransactionService.getTransactionStatus(java.lang.Long)" because "this.transactionService" is null
        //       at com.blobus.apiExterneBlobus.controllers.TransactionController.getTransactionStatus(TransactionController.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        (new TransactionController(null)).getTransactionStatus(123L);
    }

    /**
     * Method under test: {@link TransactionController#BulkCashInTransaction(RequestBodyTransactionDto[])}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testBulkCashInTransaction() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto[0]->com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto["requestDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serializeContents(ObjectArraySerializer.java:253)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serialize(ObjectArraySerializer.java:214)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serialize(ObjectArraySerializer.java:23)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.stream.ReduceOps$1ReducingSink.accept(ReduceOps.java:80)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:992)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.util.stream.ReferencePipeline.reduce(ReferencePipeline.java:657)
        //       at com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl.BulkCashInTransaction(TransactionServiceImpl.java:71)
        //       at com.blobus.apiExterneBlobus.controllers.TransactionController.BulkCashInTransaction(TransactionController.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        TransactionController transactionController = new TransactionController(
                new TransactionServiceImpl(mock(AccountRepository.class), mock(TransactionRepository.class),
                        mock(BulkRepository.class), mock(UserRepository.class)));
        AmountDto amount = new AmountDto();
        CustomerDto customer = new CustomerDto("4105551212", WalletType.BONUS);

        transactionController.BulkCashInTransaction(new RequestBodyTransactionDto[]{new RequestBodyTransactionDto(amount,
                customer, new RetailerDto("4105551212", "Encrypted Pin Code", WalletType.BONUS), "Reference", true,
                LocalDate.ofEpochDay(1L), TransactionType.CASHIN)});
    }

    /**
     * Method under test: {@link TransactionController#BulkCashInTransaction(RequestBodyTransactionDto[])}
     */
    @Test
    void testBulkCashInTransaction2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDate` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto[0]->com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto["requestDate"])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:77)
        //       at com.fasterxml.jackson.databind.SerializerProvider.reportBadDefinition(SerializerProvider.java:1306)
        //       at com.fasterxml.jackson.databind.ser.impl.UnsupportedTypeSerializer.serialize(UnsupportedTypeSerializer.java:35)
        //       at com.fasterxml.jackson.databind.ser.BeanPropertyWriter.serializeAsField(BeanPropertyWriter.java:733)
        //       at com.fasterxml.jackson.databind.ser.std.BeanSerializerBase.serializeFields(BeanSerializerBase.java:774)
        //       at com.fasterxml.jackson.databind.ser.BeanSerializer.serialize(BeanSerializer.java:178)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serializeContents(ObjectArraySerializer.java:253)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serialize(ObjectArraySerializer.java:214)
        //       at com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer.serialize(ObjectArraySerializer.java:23)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider._serialize(DefaultSerializerProvider.java:480)
        //       at com.fasterxml.jackson.databind.ser.DefaultSerializerProvider.serializeValue(DefaultSerializerProvider.java:319)
        //       at com.fasterxml.jackson.databind.ObjectMapper._writeValueAndClose(ObjectMapper.java:4624)
        //       at com.fasterxml.jackson.databind.ObjectMapper.writeValueAsString(ObjectMapper.java:3869)
        //   See https://diff.blue/R013 to resolve this issue.

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

    /**
     * Method under test: {@link TransactionController#getTransaction(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetTransaction() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "com.blobus.apiExterneBlobus.models.Transaction.getReceiveNotificatiion()" is null
        //       at com.blobus.apiExterneBlobus.services.implementations.TransactionServiceImpl.getTransaction(TransactionServiceImpl.java:128)
        //       at com.blobus.apiExterneBlobus.controllers.TransactionController.getTransaction(TransactionController.java:47)
        //   See https://diff.blue/R013 to resolve this issue.

        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(new Transaction()));
        (new TransactionController(new TransactionServiceImpl(mock(AccountRepository.class), transactionRepository,
                mock(BulkRepository.class), mock(UserRepository.class)))).getTransaction(123L);
    }

    /**
     * Method under test: {@link TransactionController#getTransaction(Long)}
     */
    @Test
    void testGetTransaction2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        TransactionRepository transactionRepository = mock(TransactionRepository.class);
        LocalDate createdDate = LocalDate.ofEpochDay(1L);
        LocalDate requestDate = LocalDate.ofEpochDay(1L);
        Account retailerTransferAccount = new Account();
        Account customerTransferAccount = new Account();
        when(transactionRepository.findById((Long) any())).thenReturn(Optional.of(new Transaction(123L, "Reference",
                createdDate, true, requestDate, TransactionStatus.ACCEPTED, TransactionType.CASHIN, 10.0d,
                TransactionCurrency.XOF, retailerTransferAccount, customerTransferAccount, new Bulk())));
        GetTransactionDto actualTransaction = (new TransactionController(
                new TransactionServiceImpl(mock(AccountRepository.class), transactionRepository, mock(BulkRepository.class),
                        mock(UserRepository.class)))).getTransaction(123L);
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

