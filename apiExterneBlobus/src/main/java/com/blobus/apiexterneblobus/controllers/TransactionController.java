package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.GetTransactionDto;
import com.blobus.apiexterneblobus.dto.RequestBodyTransactionDto;
import com.blobus.apiexterneblobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiexterneblobus.dto.TransactionDto;
import com.blobus.apiexterneblobus.services.interfaces.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.blobus.apiexterneblobus.models.enums.TransactionStatus.PENDING;
import static com.blobus.apiexterneblobus.models.enums.TransactionStatus.TERMINATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/retailer/")
public class TransactionController {
    private final TransactionService transactionService;
    @Operation(summary = "This operation allows to do a cashIn transaction")
    @PostMapping ("/cashins")
    public ResponseEntity<ResponseCashInTransactionDto> CashInTransaction(@RequestBody(required = true) RequestBodyTransactionDto requestBodyTransactionDto,
                                                                          @RequestHeader(name = "Authorization") String token){
        return ResponseEntity.ok(
                transactionService.CashInTransaction(requestBodyTransactionDto)
                );
    }

    /**
     * Cette methode retoune le status d'une transaction
     * @param transactionId
     * @return
     */
    @Operation(summary = "This operation allows to get the transaction STATUS by its transactionId")
    @GetMapping("/transactions/{transactionId}/status")
    public TransactionDto getTransactionStatus(@Parameter(description = "The transactionId is required")@PathVariable("transactionId") Long transactionId,@RequestHeader(name = "Authorization") String token){
        return  transactionService.getTransactionStatus(transactionId);
    }
    @Operation(summary = "The BulkcashIn operation allows to do many CashIntransactions in a single request ")
    @PostMapping("/bulkcashins")
    public void BulkCashInTransaction(HttpServletRequest request, @RequestBody RequestBodyTransactionDto[] requestBodyTransactionDtos,@RequestHeader(name = "Authorization") String token) throws ExecutionException, InterruptedException, JSONException {
        transactionService.BulkCashInTransaction(request,requestBodyTransactionDtos);

        //System.out.println("i am the first");

        /* return ResponseCashInTransactionDto
                .builder()
                .status(PENDING)
                .build();*/
    }

    /**
     * Cette methode affiche les information d'une transaction
     * @param transactionId
     * @return
     */
    @Operation(summary = "This operation allows to get a transaction by its ID ")
    @GetMapping("/transactions/{transactionId}")
    public GetTransactionDto getTransaction(@Parameter(description = "The transactionId is required")@PathVariable("transactionId") Long transactionId,@RequestHeader(name = "Authorization") String token){
        return transactionService.getTransaction(transactionId);
    }


}
