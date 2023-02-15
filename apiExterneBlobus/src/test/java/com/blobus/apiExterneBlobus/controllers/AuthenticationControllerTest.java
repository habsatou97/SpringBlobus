package com.blobus.apiExterneBlobus.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blobus.apiExterneBlobus.auth.*;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthenticationControllerTest {

    @Test
    void testRegister() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(userRepository, passwordEncoder, new JwtService(), authenticationManager));
        ResponseEntity<RegisterResponse> actualRegisterResult = authenticationController
                .register(new RegisterRequest());
        assertTrue(actualRegisterResult.hasBody());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        assertEquals(200, actualRegisterResult.getStatusCodeValue());
    }




    @Test
    void testAuthenticate() {
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        when(authenticationService.authenticate((AuthenticationRequest) any()))
                .thenReturn(new AuthenticationResponse("ABC123"));
        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        ResponseEntity<AuthenticationResponse> actualAuthenticateResult = authenticationController
                .authenticate(new AuthenticationRequest("42", "User Secret"));
        assertTrue(actualAuthenticateResult.hasBody());
        assertTrue(actualAuthenticateResult.getHeaders().isEmpty());
        assertEquals(200, actualAuthenticateResult.getStatusCodeValue());
        verify(authenticationService).authenticate((AuthenticationRequest) any());
    }
}

