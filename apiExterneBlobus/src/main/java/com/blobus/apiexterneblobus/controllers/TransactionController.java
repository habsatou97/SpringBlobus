package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.GetTransactionDto;
import com.blobus.apiexterneblobus.dto.RequestBodyTransactionDto;
import com.blobus.apiexterneblobus.dto.ResponseCashInTransactionDto;
import com.blobus.apiexterneblobus.dto.TransactionDto;
import com.blobus.apiexterneblobus.services.interfaces.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void BulkCashInTransaction(HttpServletRequest request, @RequestBody RequestBodyTransactionDto[] requestBodyTransactionDtos) throws ExecutionException, InterruptedException, JSONException {
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
    @GetMapping("/transactions/{transactionId}")
    public GetTransactionDto getTransaction(@PathVariable("transactionId") Long transactionId){
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping("/test-api-interne")
    public ResponseCashInTransactionDto getApiExterne(HttpServletRequest request) throws JSONException {
        String xCallbackUrl = request.getHeader("x-callback-url");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject JsonObject = new JSONObject();

        ResponseCashInTransactionDto responseCashInTransactionDto =
                ResponseCashInTransactionDto
                        .builder()
                        .status(TERMINATED)
                        .build();

        JsonObject.put("userName", "loumanekh");
        JsonObject.put("name", "el");
        JsonObject.put("surname","elel");
        JsonObject.put("emailAddress", "el@gmail.com");
        JsonObject.put("isActive","true");
        JsonObject.put("password", "123elel");

        HttpEntity<String> requestHttp =
                new HttpEntity<String>(JsonObject.toString(), headers);

        String responseEntityPerson = restTemplate.postForObject(xCallbackUrl, requestHttp, String.class);

        return responseCashInTransactionDto;
    }

}
