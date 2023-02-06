package com.blobus.apiExterneBlobus.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blobus.apiExterneBlobus.auth.AuthenticationRequest;
import com.blobus.apiExterneBlobus.auth.AuthenticationResponse;
import com.blobus.apiExterneBlobus.auth.AuthenticationService;
import com.blobus.apiExterneBlobus.auth.RegisterRequest;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class AuthenticationControllerTest {
    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegister() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository repository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        JwtService jwtService = new JwtService();
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(repository, passwordEncoder, jwtService, new ProviderManager(new ArrayList<>())));
        authenticationController.register(new RegisterRequest("Jane", "Doe", "42", "User Secret", "jane.doe@example.org"));
    }

    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    void testRegister2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(userRepository, passwordEncoder, new JwtService(), authenticationManager));
        ResponseEntity<AuthenticationResponse> actualRegisterResult = authenticationController
                .register(new RegisterRequest("Jane", "Doe", "42", "User Secret", "jane.doe@example.org"));
        assertTrue(actualRegisterResult.hasBody());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        assertEquals(200, actualRegisterResult.getStatusCodeValue());
        verify(userRepository).save((User) any());
    }

    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegister3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.config.JwtService.generateToken(org.springframework.security.core.userdetails.UserDetails)" because "this.jwtService" is null
        //       at com.blobus.apiExterneBlobus.auth.AuthenticationService.register(AuthenticationService.java:32)
        //       at com.blobus.apiExterneBlobus.controllers.AuthenticationController.register(AuthenticationController.java:25)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(userRepository, new BCryptPasswordEncoder(), null, authenticationManager));
        authenticationController
                .register(new RegisterRequest("Jane", "Doe", "42", "User Secret", "jane.doe@example.org"));
    }

    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    void testRegister4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(new User());
        JwtService jwtService = mock(JwtService.class);
        when(jwtService.generateToken((UserDetails) any())).thenReturn("ABC123");

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(userRepository, new BCryptPasswordEncoder(), jwtService, authenticationManager));
        ResponseEntity<AuthenticationResponse> actualRegisterResult = authenticationController
                .register(new RegisterRequest("Jane", "Doe", "42", "User Secret", "jane.doe@example.org"));
        assertTrue(actualRegisterResult.hasBody());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        assertEquals(200, actualRegisterResult.getStatusCodeValue());
        assertEquals("ABC123", actualRegisterResult.getBody().getToken());
        verify(userRepository).save((User) any());
        verify(jwtService).generateToken((UserDetails) any());
    }

    /**
     * Method under test: {@link AuthenticationController#register(RegisterRequest)}
     */
    @Test
    void testRegister5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        AuthenticationService authenticationService = mock(AuthenticationService.class);
        when(authenticationService.register((RegisterRequest) any())).thenReturn(new AuthenticationResponse("ABC123"));
        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        ResponseEntity<AuthenticationResponse> actualRegisterResult = authenticationController
                .register(new RegisterRequest("Jane", "Doe", "42", "User Secret", "jane.doe@example.org"));
        assertTrue(actualRegisterResult.hasBody());
        assertTrue(actualRegisterResult.getHeaders().isEmpty());
        assertEquals(200, actualRegisterResult.getStatusCodeValue());
        verify(authenticationService).register((RegisterRequest) any());
    }

    /**
     * Method under test: {@link AuthenticationController#authenticate(AuthenticationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthenticate() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: A parent AuthenticationManager or a list of AuthenticationProviders is required
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository repository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        JwtService jwtService = new JwtService();
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(repository, passwordEncoder, jwtService, new ProviderManager(new ArrayList<>())));
        authenticationController.authenticate(new AuthenticationRequest("42", "User Secret"));
    }

    /**
     * Method under test: {@link AuthenticationController#authenticate(AuthenticationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthenticate2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.security.authentication.ProviderNotFoundException: Aucun AuthenticationProvider n'a été trouvé pour org.springframework.security.authentication.UsernamePasswordAuthenticationToken
        //       at com.blobus.apiExterneBlobus.auth.AuthenticationService.authenticate(AuthenticationService.java:39)
        //       at com.blobus.apiExterneBlobus.controllers.AuthenticationController.authenticate(AuthenticationController.java:31)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
        authenticationProviderList.add(new RunAsImplAuthenticationProvider());
        ProviderManager authenticationManager = new ProviderManager(authenticationProviderList);
        UserRepository repository = mock(UserRepository.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        AuthenticationController authenticationController = new AuthenticationController(
                new AuthenticationService(repository, passwordEncoder, new JwtService(), authenticationManager));
        authenticationController.authenticate(new AuthenticationRequest("42", "User Secret"));
    }

    /**
     * Method under test: {@link AuthenticationController#authenticate(AuthenticationRequest)}
     */
    @Test
    void testAuthenticate3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

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

