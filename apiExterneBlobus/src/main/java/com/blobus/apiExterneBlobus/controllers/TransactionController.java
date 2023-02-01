package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.RequestBodyTransactionDto;
import com.blobus.apiExterneBlobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiExterneBlobus.dto.TransactionDto;
import com.blobus.apiExterneBlobus.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1")
public class TransactionController {
    private final TransactionService transactionService;
    @GetMapping("/cashins")
    public ResponseEntity<ResponseCashInTransactionDto> CashInTransaction(@RequestBody RequestBodyTransactionDto requestBodyTransactionDto){
        return ResponseEntity.ok(
                transactionService.CashInTransaction(requestBodyTransactionDto)
                );
    }

    /**
     * Cette methodeb retoune le status d'une transaction
     * @param transactionId
     * @return
     */
    @GetMapping("transactions/{transactionId}/status")
    public TransactionDto getTransactionStatus(@PathVariable("transactionId") Long transactionId){
        return  transactionService.getTransactionStatus(transactionId);
    }
    @GetMapping("/bulkcashins")
    public ResponseEntity<ResponseCashInTransactionDto> BulkCashInTransaction(@RequestBody RequestBodyTransactionDto[] requestBodyTransactionDtos){
        return ResponseEntity.ok(
                transactionService.BulkCashInTransaction(requestBodyTransactionDtos)
                );
    }

}
