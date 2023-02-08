package com.blobus.apiExterneBlobus.models.enums;

public enum ErrorCode{
    CUSTOMER_ACCOUNT_DOES_NOT_EXIST("2000","Customer account does not exist"),
    CUSTOMER_MSISDN_IS_INVALID("2001","Customer msisdn is invalid"),
    RETAILER_ACCOUNT_DOES_NOT_EXIST("2002","Retailer account does not exist"),
    YOUR_PIN_SHOULD_BE_CHANGED("2010","Your pin should be changed"),
    INVALID_PIN_CODE_TWO_REMAINING_TENTATIVES_LEFT("2011","Invalid pin code , 2 remaining tentatives left"),
    INVALID_PIN_CODE_ONE_REMAINING_TENTATIVES_LEFT("2012","Invalid pin code , 1 remaining tentatives left"),
    INVALID_PIN_CODE_YOUR_ACCOUNT_IS_BLOCKED("2013","Invalid pin code , your account is blocked"),
    INVALID_PIN_CODE("2014","Invalid pin code"),
    BALANCE_INSUFFICIENT("2020","Balance insufficient"),
    BALANCE_MAXIMAL("2023","Balance maximal"),
    TRANSACTION_NOT_ALLOWED("2041","Transaction not allowed"),
    FAILED_TO_DECRIPT_RECEIVED_MESSAGE_PLEASE_RETRIEVE_A_NEW_PUBLIC_KEY("4004","Failed to decript received message please retrieve a new public key");


    private String errorCode;
    private String errorMessage;
    ErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage =errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
