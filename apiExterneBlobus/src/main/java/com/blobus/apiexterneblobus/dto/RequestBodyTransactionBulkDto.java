package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RequestBodyTransactionBulkDto {
    private List<AmountCustomerDto> amountCustomer;
    private RetailerDto retailer;
    private String reference;
    private Boolean receiveNotificatiion;
    private LocalDate requestDate;
    private TransactionType transactionType;
}
