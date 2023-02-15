package com.blobus.apiExterneBlobus.auth;

import com.blobus.apiExterneBlobus.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String phoneNumber;
  private List<Role> roles;
  private String ninea;
}
