package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import lombok.Data;

@Data
public class RetailerDto {
    private String retailerTransferAccountPhoneNumber;
    private String retailerTransferAccountEncryptedPinCode;
    private WalletType retailerTransferAccountWalletType;
}
