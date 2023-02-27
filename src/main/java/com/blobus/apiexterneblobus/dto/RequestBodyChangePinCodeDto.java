package com.blobus.apiexterneblobus.dto;


import lombok.*;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class RequestBodyChangePinCodeDto {

    private String encryptedNewPinCode;

    private String encryptedPinCode;

}
