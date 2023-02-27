package com.blobus.apiexterneblobus.dto;

import com.blobus.apiexterneblobus.models.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserWithNineaDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String ninea;
    private List<Role> roles;
}
