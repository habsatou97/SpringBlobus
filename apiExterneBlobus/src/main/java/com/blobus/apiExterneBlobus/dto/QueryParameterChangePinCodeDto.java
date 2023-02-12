package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.CustomerType;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Data
@Getter
@Setter

public class QueryParameterChangePinCodeDto {

    @NonNull
    private String msisdn;
    private CustomerType customerType;
}
