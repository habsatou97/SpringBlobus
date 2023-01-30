package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import com.blobus.apiExterneBlobus.models.enums.TransactionType;
import com.blobus.apiExterneBlobus.models.enums.WalletType;

import java.util.Date;

public class RequestBodyTransactionDto {
    private String reference;
    private Boolean receiveNotificatiion;
    private Date requestDate;
    private Double amount;
    private TransactionCurrency currency;
    private TransactionType transactionType;
    private Long retailerTransferAccountId;
    private String retailerTransferAccountEncryptedPinCode;
    private WalletType retailerTransferAccountWalletType;
    private Long customerTransferAccountId;
    private WalletType customerTransferAccountWalletType;
}
