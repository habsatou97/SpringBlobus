package com.blobus.apiexterneblobus.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReturnDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
