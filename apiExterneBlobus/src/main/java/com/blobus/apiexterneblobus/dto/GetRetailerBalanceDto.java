package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.WalletType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRetailerBalanceDto {
    private String encryptedPinCode;
    private String phoneNumber;
    private WalletType walletType;
}
