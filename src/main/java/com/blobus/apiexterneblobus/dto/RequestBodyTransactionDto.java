package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestBodyTransactionDto {
    private AmountDto amount;
    private CustomerDto customer;
    private RetailerDto retailer;
    private String reference;
    private Boolean receiveNotificatiion;
    private LocalDate requestDate;
    private TransactionType transactionType;

}
