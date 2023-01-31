package com.blobus.apiExterneBlobus.services.interfaces;

import com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto;
import com.blobus.apiExterneBlobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiExterneBlobus.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface TransactionService {
    // pour convertir le requestBody en Transaction
    Transaction convertDtoToEntityTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
    // pour effectuer une transaction CashIn
    ResponseCashInTransactionDto CashInTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
}
