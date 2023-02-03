package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
@Data
@SuperBuilder
@JsonInclude(NON_NULL)
@NoArgsConstructor
public class ResponseCashInTransactionDto {
    private String reference;
    private TransactionStatus status;
    private Long transactionId;
    private String errorCode;
    private String errorMessage;
    // pour la reponse de BulkCashIn
    private Long bulkId;
    private String description;
}
