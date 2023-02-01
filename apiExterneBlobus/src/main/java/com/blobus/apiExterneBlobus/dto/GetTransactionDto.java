package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class GetTransactionDto {

    private AmountDto amount;
    private Date createdAt;
    private Customer customer;
    private User partner;
    private boolean receiveNotification;
    private String reference;
    private Date requestDate;
    private String status;
    private Long transactionId;
    private  String type;

}
