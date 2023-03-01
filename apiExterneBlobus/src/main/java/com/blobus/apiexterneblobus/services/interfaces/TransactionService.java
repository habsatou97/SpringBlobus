package com.blobus.apiexterneblobus.services.interfaces;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.models.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;

import java.util.List;

public interface TransactionService {
    // pour convertir le requestBody en Transaction
    Transaction convertDtoToEntityTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
    // pour effectuer une transaction CashIn
    ResponseCashInTransactionDto CashInTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
    // pour effectuer une transaction BulkCashIn
    List<ResponseCashInTransactionDto> BulkCashInTransaction(HttpServletRequest request, RequestBodyTransactionBulkDto requestBodyTransactionDto) throws InterruptedException, JSONException;

    public TransactionDto getTransactionStatus(Long transactionId);

    public GetTransactionDto getTransaction(Long transactionId);
}
