package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ResponseChangePinCodeDto {
    private String errorCode;
    private String msisdn;
    private String encryptedNewPinCode;
    private  String encryptedPinCode;
    private CustomerType customerType;
    private HttpStatus Status;
}
