package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class ResponseBulkCashInTransactionDto {
    private Long transactionId;
    private TransactionStatus status;
    private String errorCode;
    private String errorMessage;
}
