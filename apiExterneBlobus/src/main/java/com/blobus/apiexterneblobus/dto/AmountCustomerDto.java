package com.blobus.apiexterneblobus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmountCustomerDto {
    private AmountDto amount;
    private CustomerDto customer;
}
