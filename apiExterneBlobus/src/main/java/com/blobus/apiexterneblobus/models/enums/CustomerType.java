package com.blobus.apiexterneblobus.models.enums;

public enum CustomerType {
    CUSTOMER("costumer"),
    RETAILER("retailer");
    private String type;
     CustomerType(String type){
        this.type=type;
    }
}
