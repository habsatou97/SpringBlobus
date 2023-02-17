package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerializableSchema
public class WalletTypeDto {
    private WalletType walletType;
}
