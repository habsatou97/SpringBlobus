package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import lombok.*;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class RequestBodyChangePinCodeDto {
    @NonNull
    private String encryptedNewPinCode;
   @NonNull
    private String encryptedPinCode;

}
