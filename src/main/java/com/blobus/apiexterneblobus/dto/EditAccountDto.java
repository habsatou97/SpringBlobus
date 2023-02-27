package com.blobus.apiexterneblobus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditAccountDto {
    private  double balance;
    private String phoneNumber;
    private String encryptedPinCode;
}
