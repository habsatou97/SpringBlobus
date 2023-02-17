package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.TransactionCurrency;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AmountDto {
    private Double value;
    private TransactionCurrency currency;
}
