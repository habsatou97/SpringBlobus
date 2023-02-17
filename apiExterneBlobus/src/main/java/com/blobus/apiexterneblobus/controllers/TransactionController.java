package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.GetTransactionDto;
import com.blobus.apiexterneblobus.dto.RequestBodyTransactionDto;
import com.blobus.apiexterneblobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiexterneblobus.dto.TransactionDto;
import com.blobus.apiexterneblobus.services.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * Cette methode retoune le status d'une transaction
     * @param transactionId
     * @return
     */
    @GetMapping("/transactions/{transactionId}/status")
    public TransactionDto getTransactionStatus(@PathVariable("transactionId") Long transactionId){
        return  transactionService.getTransactionStatus(transactionId);
    }
    @PostMapping("/bulkcashins")
    public ResponseEntity<ResponseCashInTransactionDto> BulkCashInTransaction(@RequestBody RequestBodyTransactionDto[] requestBodyTransactionDtos){
        return ResponseEntity.ok(
                transactionService.BulkCashInTransaction(requestBodyTransactionDtos)
                );
    }

    /**
     * Cette methode affiche les information d'une transaction
     * @param transactionId
     * @return
     */
    @GetMapping("/transactions/{transactionId}")
    public GetTransactionDto getTransaction(@PathVariable("transactionId") Long transactionId){
        return transactionService.getTransaction(transactionId);
    }

}
