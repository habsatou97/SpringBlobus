package com.blobus.apiExterneBlobus.dto;


import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBodyUserProfileDto {
    private CustomerType customerType;
    private WalletType walletType;
    private String phoneNumber;

}
