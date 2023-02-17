package com.blobus.apiExterneBlobus.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@RequiredArgsConstructor
@Data
@Getter
@Setter
public class HeaderDto {

    LocalDateTime date;           //Date d'envoi de la requete
    String  Content_Type;             //Html ou json
    }
