package com.blobus.apiExterneBlobus.models.enums;

public enum TransactionStatus {
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED"),
    FAILED("FAILED"),
    INITIATED("INITIATED"),
    PENDING("PENDING"),
    PRE_INITIATED("PRE_INITIATED"),
    SUCCESS("SUCCESS");
    private String status;


    TransactionStatus(String status) {
        this.status = status;
    }
}
