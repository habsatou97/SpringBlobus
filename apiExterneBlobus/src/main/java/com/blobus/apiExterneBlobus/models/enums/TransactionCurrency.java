package com.blobus.apiExterneBlobus.models.enums;

public enum TransactionCurrency {
    XOF("XOF"),
    USD("USD"),
    EURO("EURO");

    private String currency;
    TransactionCurrency(String currency) {
        this.currency = currency;
    }

}