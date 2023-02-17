package com.blobus.apiexterneblobus.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetTransactionDto {

    private AmountDto amount;
    private LocalDate createdAt;
    private CustomerEditCreateDto customer;
    private UserDto partner;
    private boolean receiveNotification;
    private String reference;
    private LocalDate requestDate;
    private String status;
    private Long transactionId;
    private  String type;

}
