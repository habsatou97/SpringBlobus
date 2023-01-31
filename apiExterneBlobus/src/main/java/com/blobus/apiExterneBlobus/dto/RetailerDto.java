package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Data;

@Data
public class RetailerDto {
    private String phoneNumber;
    private String encryptedPinCode;
    private WalletType walletType;
}
