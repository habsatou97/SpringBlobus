package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEditCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<CreateOrEditAccountDto> transferAccounts = new ArrayList<>();
    CustomerEditCreateDto(String email){
        this.email = email;
    }
}
