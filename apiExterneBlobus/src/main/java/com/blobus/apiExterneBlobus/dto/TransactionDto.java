package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private TransactionStatus status;
}
