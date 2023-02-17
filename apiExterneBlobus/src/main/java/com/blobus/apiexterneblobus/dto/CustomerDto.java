package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.WalletType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDto {
    private String phoneNumber;
    private WalletType walletType;
}
