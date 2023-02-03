package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
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
