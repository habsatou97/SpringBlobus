package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    private String phoneNumber;
    private WalletType walletType;
}
