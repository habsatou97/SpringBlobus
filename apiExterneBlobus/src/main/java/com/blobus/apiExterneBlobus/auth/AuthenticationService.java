package com.blobus.apiExterneBlobus.auth;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    if(
                    request.getFirstname() == null ||
                    request.getLastname() == null ||
                    request.getEmail() == null)
    {
      throw new IllegalArgumentException("There is some empty user properties.");
    }

    if(request.getPhoneNumber().length() != 9){
      throw new IllegalArgumentException("The phone number must be 9 characters.");

    }


    String userId = RandomStringUtils.random(5,"azertyuiopqsdfghjklmwxcvbn1223456789");
    String userSecret = RandomStringUtils.random(4,"123456789");

    // verify if the phone number and the email exists
    Optional<User> userOptional = repository.findUserByEmail(request.getEmail());
    Optional<User> userOptional1 = repository.findUserByPhoneNumber(user.getPhoneNumber());
    if(userOptional.isPresent() || userOptional1.isPresent()){
      // if phone number or email exists, throws an exception.
      throw new IllegalStateException("Email and/or phone number exists.");
    }

    user.setFirstName(request.getFirstname());
    user.setLastName(request.getLastname());
    user.setEmail(request.getEmail());
    user.setUserSecret(passwordEncoder.encode(userSecret));
    user.setPhoneNumber(request.getPhoneNumber());
    user.setRoles(request.getRoles());
    if (request.getNinea() != null && !request.getNinea().isBlank() && request.getNinea().length() != 0){
        user.setNinea(request.getNinea());
    }


    User user1 = repository.save(user);
    user1.setUserId(user1.getId()+userId);
    repository.save(user1);
    //var jwtToken = jwtService.generateToken(user);
    return new RegisterResponse(user.getUserId(), userSecret);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUserId(),
            request.getUserSecret()
        )
    );
    var user = repository.findByUserId(request.getUserId())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }





}
