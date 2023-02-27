package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private TransactionStatus status;
}
