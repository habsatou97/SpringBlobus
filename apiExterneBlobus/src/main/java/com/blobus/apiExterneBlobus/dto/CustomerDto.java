package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Data;

@Data
public class CustomerDto {
    private String phoneNumber;
    private WalletType walletType;
}
