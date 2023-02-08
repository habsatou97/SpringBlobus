package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrEditAccountDto {
    private WalletType walletType;
    private String phoneNumber;
    private String encryptedPinCode;
    private double balance;
}
