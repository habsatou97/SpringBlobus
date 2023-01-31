package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import lombok.Data;

import java.util.Date;
@Data
public class RequestBodyTransactionDto {
    private AmountDto amount;
    private CustomerDto customer;
    private RetailerDto retailer;
    private String reference;
    private Boolean receiveNotificatiion;
    private Date requestDate;
    private TransactionType transactionType;

}
