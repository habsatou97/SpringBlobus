package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
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
