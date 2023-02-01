package com.blobus.apiExterneBlobus.models.enums;


public enum TransactionType {
    CASHIN("CASHIN"),
    BULKCASHIN("BULKCASHIN"),
    CASHOUT("CASHOUT"),
    CASHOUT_ONE_STEP("CASHOUT_ONE_STEP"),
    MERCHANT_PAYMENT("MERCHANT_PAYMENT"),
    TRANSFERT_WITH_CODE("TRANSFERT_WITH_CODE"),
    WEB_PAYMENT("WEB_PAYMENT");
    private String type;

    TransactionType(String type) {
        this.type = type;
    }
}
