package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto;
import com.blobus.apiExterneBlobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiExterneBlobus.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1")
public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping ("/cashins")
    public ResponseEntity<ResponseCashInTransactionDto> CashInTransaction(@RequestBody RequestBodyTransactionDto requestBodyTransactionDto){
        return ResponseEntity.ok(
                transactionService.CashInTransaction(requestBodyTransactionDto)
                );
    }
}
