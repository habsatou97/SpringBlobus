package com.blobus.apiExterneBlobus.dto;

import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerializableSchema
public class WalletTypeDto {
    private WalletType walletType;
}
