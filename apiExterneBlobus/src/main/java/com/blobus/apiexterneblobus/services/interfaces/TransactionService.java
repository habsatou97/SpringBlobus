package com.blobus.apiexterneblobus.services.interfaces;

import com.blobus.apiexterneblobus.dto.GetTransactionDto;
import com.blobus.apiexterneblobus.dto.RequestBodyTransactionDto;
import com.blobus.apiexterneblobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiexterneblobus.dto.TransactionDto;
import com.blobus.apiexterneblobus.models.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;

public interface TransactionService {
    // pour convertir le requestBody en Transaction
    Transaction convertDtoToEntityTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
    // pour effectuer une transaction CashIn
    ResponseCashInTransactionDto CashInTransaction(RequestBodyTransactionDto requestBodyTransactionDto);
    // pour effectuer une transaction BulkCashIn
    void BulkCashInTransaction(HttpServletRequest request, RequestBodyTransactionDto[] requestBodyTransactionDto) throws InterruptedException, JSONException;

    public TransactionDto getTransactionStatus(Long transactionId);

    public GetTransactionDto getTransaction(Long transactionId);
}
