package com.blobus.apiExterneBlobus.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class KeyDto {
    String key;
    Integer keySize;
    String keyType;
}
