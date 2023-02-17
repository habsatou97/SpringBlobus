package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
