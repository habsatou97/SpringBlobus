package com.blobus.apiexterneblobus.auth;
import com.blobus.apiexterneblobus.config.JwtService;
import com.blobus.apiexterneblobus.models.User;
import com.blobus.apiexterneblobus.models.enums.Role;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) {
    var user = new User();

    // verifying if firstname, lastname and the email aren't empty
    if(
                    request.getFirstname() == null ||
                    request.getLastname() == null ||
                    request.getEmail() == null)
    {
      throw new IllegalArgumentException("There is some empty user properties.");
    }

    // verify if the phone number have exactly nine characters.
    if(request.getPhoneNumber().length() != 9){
      throw new IllegalArgumentException("The phone number must be equals than 9 characters.");

    }


    // userId and userSecret
    String userId = RandomStringUtils.random(5,"azertyuiopqsdfghjklmwxcvbn1223456789");
    String userSecret = RandomStringUtils.random(4,"123456789");

    // verify if the phone number and the email exists
    Optional<User> userOptional = repository.findUserByEmail(request.getEmail());
    Optional<User> userOptional1 = repository.findUserByPhoneNumber(user.getPhoneNumber());
    if(userOptional.isPresent() || userOptional1.isPresent()){
      // if phone number or email exists, throws an exception.
      throw new IllegalStateException("Email and/or phone number exists.");
    }

    // set user properties
    user.setFirstName(request.getFirstname());
    user.setLastName(request.getLastname());
    user.setEmail(request.getEmail());
    user.setUserSecret(passwordEncoder.encode(userSecret));
    user.setPhoneNumber(request.getPhoneNumber());

    // verify if the ninea is given
    if (request.getNinea() != null && !request.getNinea().isBlank() && request.getNinea().length() != 0){
        // save ninea if yes
        user.setNinea(request.getNinea());
        user.getRoles().add(Role.RETAILER);
    }


    // save user and get the user with his id
    User user1 = repository.save(user);

    // update userId by concatenate the generated userId and the id of the user
    user1.setUserId(user1.getId()+userId);
//    user1.getRoles().add(Role.ADMIN);
    repository.save(user1);

    // now return the userId and userSecret
    return new RegisterResponse(user.getUserId(), userSecret);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // authenticate user with his username (userId) and his password(UserSecret)
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUserId(),
            request.getUserSecret()
        )
    );

    // get authenticate user and save it in user variable
    var user = repository.findByUserId(request.getUserId())
        .orElseThrow();

    // create a token
    var jwtToken = jwtService.generateToken(user);

    // build and return token
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }





}
