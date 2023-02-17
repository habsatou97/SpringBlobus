package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetailerDto {
    private String phoneNumber;
    private String encryptedPinCode;
    private WalletType walletType;
}
