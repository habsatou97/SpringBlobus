package com.blobus.apiExterneBlobus.dto;


import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBodyUserProfileDto {
    private WalletType walletType;
    private String msisdn;
    private String type;
    private String firstName;
    private String lastName;
    private String userId;
    private double balance;
    private boolean suspended;



}
