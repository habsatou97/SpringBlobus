package com.blobus.apiExterneBlobus.auth;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.dto.UserDto;
import com.blobus.apiExterneBlobus.dto.UserRegisterDto;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    String userId = RandomStringUtils.random(5,"azertyuiopqsdfghjklmwxcvbn1223456789");
    String userSecret = RandomStringUtils.random(4,"123456789");
    Optional<User> userOptional = repository.findUserByEmail(request.getEmail());
    Optional<User> userOptional1 = repository.findUserByPhoneNumber(user.getPhoneNumber());
    if(userOptional.isPresent() || userOptional1.isPresent()){
      throw new IllegalStateException("Email et/ou mot de passe déjà existant.");
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
