package com.blobus.apiexterneblobus.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBodyUserProfileDto {
    private AmountDto balance;
    private String firstName;
    private String lastName;
    private String msisdn;
    private boolean suspended;
    private String type;
    private String userId;




}
