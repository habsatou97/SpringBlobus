package com.blobus.apiExterneBlobus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEditCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    CustomerEditCreateDto(String email){
        this.email = email;
    }
}
