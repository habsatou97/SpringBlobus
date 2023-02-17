package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.WalletType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateOrEditAccountDto {
    private WalletType walletType;
    private String phoneNumber;
    private String encryptedPinCode;
    private double balance;
}
