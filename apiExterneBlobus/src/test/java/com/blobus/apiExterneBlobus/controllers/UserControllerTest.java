package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.AmountDto;
import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.Account;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.Transaction;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.models.enums.WalletType;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserServiceImpl service;
    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AccountRepository accountRepository;

    User user1 = new User(
            3l,
            "El Seydi",
            "Ba",
            "em-seydi.ba@avimtoo.com",
            Role.RETAILER,
            "vimto1245",
            "718954362",
            "fzivbedfegjd",
            "fohfgfyf78");

    User user2 = new User(
            2l,
            "Cheikh Yangkhouba",
            "Cisse",
            "cheikh-yangkhouba.cisse@avimtoo.com",
            Role.ADMIN,
            "vimto1245",
            "708954362",
            "fzivbeegjd",
            "fohfyf78");
    User user3 = new User(
            4l,
            "Cheikh Yangkhouba",
            "Cisse",
            "cheikh-yangkhouba.cisse@avimtoo.com",
            Role.RETAILER,
            "vimto1245",
            "708954362",
            "fzivbeegjd",
            "fohfyf78");

    @Test
    void getAllUsers() throws Exception {

        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        List<User> users1 = userRepository.findAll();
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Cheikh Yangkhouba")));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<User>> actualAllUsers = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class)))
                .getAllUsers();
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllUsers2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.getAllUsers()" because "this.userService" is null
        //       at com.blobus.apiExterneBlobus.controllers.UserController.getAllUsers(UserController.java:39)
        //   See https://diff.blue/R013 to resolve this issue.

        (new UserController(null, mock(UserRepository.class))).getAllUsers();
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.getAllUsers()).thenReturn(new ArrayList<>());
        ResponseEntity<List<User>> actualAllUsers = (new UserController(userServiceImpl, mock(UserRepository.class)))
                .getAllUsers();
        assertTrue(actualAllUsers.hasBody());
        assertEquals(200, actualAllUsers.getStatusCodeValue());
        assertTrue(actualAllUsers.getHeaders().isEmpty());
        verify(userServiceImpl).getAllUsers();
    }

    @Test
    void getAllRetailer() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));
        List<User> userList = new ArrayList<>();
        for (User user : users) {
            if (user.getRoles().contains(Role.RETAILER)) {
                userList.add(user);
            }
        }
        Mockito.when(userService.getAllRetailer()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/retailers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Cheikh Yangkhouba")));

    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    void testGetAllRetailer() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        ArrayList<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<User>> actualAllRetailer = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class)))
                .getAllRetailer();
        assertEquals(userList, actualAllRetailer.getBody());
        assertEquals(200, actualAllRetailer.getStatusCodeValue());
        assertTrue(actualAllRetailer.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    void testGetAllRetailer2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User());
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<User>> actualAllRetailer = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class)))
                .getAllRetailer();
        assertTrue(actualAllRetailer.hasBody());
        assertEquals(200, actualAllRetailer.getStatusCodeValue());
        assertTrue(actualAllRetailer.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllRetailer3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.getAllRetailer()" because "this.userService" is null
        //       at com.blobus.apiExterneBlobus.controllers.UserController.getAllRetailer(UserController.java:70)
        //   See https://diff.blue/R013 to resolve this issue.

        (new UserController(null, mock(UserRepository.class))).getAllRetailer();
    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    void testGetAllRetailer4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.getAllRetailer()).thenReturn(new ArrayList<>());
        ResponseEntity<List<User>> actualAllRetailer = (new UserController(userServiceImpl, mock(UserRepository.class)))
                .getAllRetailer();
        assertTrue(actualAllRetailer.hasBody());
        assertEquals(200, actualAllRetailer.getStatusCodeValue());
        assertTrue(actualAllRetailer.getHeaders().isEmpty());
        verify(userServiceImpl).getAllRetailer();
    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAllRetailer5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.models.User.getRoles()" because "user" is null
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.getAllRetailer(UserServiceImpl.java:132)
        //       at com.blobus.apiExterneBlobus.controllers.UserController.getAllRetailer(UserController.java:70)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<User> userList = new ArrayList<>();
        userList.add(null);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        (new UserController(new UserServiceImpl(userRepository, mock(AccountRepository.class)),
                mock(UserRepository.class))).getAllRetailer();
    }

    /**
     * Method under test: {@link UserController#getAllRetailer()}
     */
    @Test
    void testGetAllRetailer6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(123L, "Jane", "Doe", "jane.doe@example.org", Role.RETAILER, "Ninea", "4105551212", "42",
                "User Secret"));
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        ResponseEntity<List<User>> actualAllRetailer = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class)))
                .getAllRetailer();
        List<User> body = actualAllRetailer.getBody();
        assertEquals(userList, body);
        assertEquals(1, body.size());
        assertEquals(200, actualAllRetailer.getStatusCodeValue());
        assertTrue(actualAllRetailer.getHeaders().isEmpty());
        verify(userRepository).findAll();
    }

    /**
     * Method under test: {@link UserController#addUser(User)}
     */
    @Test
    void testAddUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserController userController = new UserController(
                new UserServiceImpl(mock(UserRepository.class), mock(AccountRepository.class)), mock(UserRepository.class));
        ResponseEntity<User> actualAddUserResult = userController.addUser(new User());
        assertNull(actualAddUserResult.getBody());
        assertEquals(200, actualAddUserResult.getStatusCodeValue());
        assertTrue(actualAddUserResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link UserController#addUser(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.addSingleUser(com.blobus.apiExterneBlobus.models.User)" because "this.userService" is null
        //       at com.blobus.apiExterneBlobus.controllers.UserController.addUser(UserController.java:81)
        //   See https://diff.blue/R013 to resolve this issue.

        UserController userController = new UserController(null, mock(UserRepository.class));
        userController.addUser(new User());
    }

    /**
     * Method under test: {@link UserController#addUser(User)}
     */
    @Test
    void testAddUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.addSingleUser((User) any())).thenReturn(new User());
        UserController userController = new UserController(userServiceImpl, mock(UserRepository.class));
        User user = new User();
        ResponseEntity<User> actualAddUserResult = userController.addUser(user);
        assertEquals(user, actualAddUserResult.getBody());
        assertTrue(actualAddUserResult.getHeaders().isEmpty());
        assertEquals(200, actualAddUserResult.getStatusCodeValue());
        verify(userServiceImpl).addSingleUser((User) any());
    }

    /**
     * Method under test: {@link UserController#updateUser(User, Long)}
     */
    @Test
    void testUpdateUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `org.springframework.security.core.GrantedAuthority` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 226] (through reference chain: com.blobus.apiExterneBlobus.models.User["authorities"]->java.util.ImmutableCollections$List12[1])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
        //       at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1909)
        //       at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:408)
        //       at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1354)
        //       at com.fasterxml.jackson.databind.deser.AbstractDeserializer.deserialize(AbstractDeserializer.java:274)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer._deserializeFromArray(CollectionDeserializer.java:359)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:272)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:28)
        //       at com.fasterxml.jackson.databind.deser.impl.SetterlessProperty.deserializeAndSet(SetterlessProperty.java:134)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)
        //       at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)
        //       at com.fasterxml.jackson.databind.ObjectReader._bindAndClose(ObjectReader.java:2105)
        //       at com.fasterxml.jackson.databind.ObjectReader.readValue(ObjectReader.java:1481)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.saveAndFlush((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        UserController userController = new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class));
        User user = new User();
        ResponseEntity<User> actualUpdateUserResult = userController.updateUser(user, 123L);
        assertEquals(user, actualUpdateUserResult.getBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserResult.getStatusCodeValue());
        verify(userRepository).saveAndFlush((User) any());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserController#updateUser(User, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `org.springframework.security.core.GrantedAuthority` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 226] (through reference chain: com.blobus.apiExterneBlobus.models.User["authorities"]->java.util.ImmutableCollections$List12[1])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
        //       at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1909)
        //       at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:408)
        //       at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1354)
        //       at com.fasterxml.jackson.databind.deser.AbstractDeserializer.deserialize(AbstractDeserializer.java:274)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer._deserializeFromArray(CollectionDeserializer.java:359)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:272)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:28)
        //       at com.fasterxml.jackson.databind.deser.impl.SetterlessProperty.deserializeAndSet(SetterlessProperty.java:134)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)
        //       at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)
        //       at com.fasterxml.jackson.databind.ObjectReader._bindAndClose(ObjectReader.java:2105)
        //       at com.fasterxml.jackson.databind.ObjectReader.readValue(ObjectReader.java:1481)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.blobus.apiExterneBlobus.exception.ResourceNotFoundException: updated failled ,user_id not found
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.lambda$updateSingleUser$0(UserServiceImpl.java:51)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.updateSingleUser(UserServiceImpl.java:51)
        //       at com.blobus.apiExterneBlobus.controllers.UserController.updateUser(UserController.java:92)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.saveAndFlush((User) any())).thenReturn(new User());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        UserController userController = new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class));
        userController.updateUser(new User(), 123L);
    }

    /**
     * Method under test: {@link UserController#updateUser(User, Long)}
     */
    @Test
    void testUpdateUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   org.springframework.http.converter.HttpMessageConversionException: Type definition error: [simple type, class org.springframework.security.core.GrantedAuthority]
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `org.springframework.security.core.GrantedAuthority` (no Creators, like default constructor, exist): abstract types either need to be mapped to concrete types, have custom deserializer, or contain additional type information
        //    at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 226] (through reference chain: com.blobus.apiExterneBlobus.models.User["authorities"]->java.util.ImmutableCollections$List12[1])
        //       at com.fasterxml.jackson.databind.exc.InvalidDefinitionException.from(InvalidDefinitionException.java:67)
        //       at com.fasterxml.jackson.databind.DeserializationContext.reportBadDefinition(DeserializationContext.java:1909)
        //       at com.fasterxml.jackson.databind.DatabindContext.reportBadDefinition(DatabindContext.java:408)
        //       at com.fasterxml.jackson.databind.DeserializationContext.handleMissingInstantiator(DeserializationContext.java:1354)
        //       at com.fasterxml.jackson.databind.deser.AbstractDeserializer.deserialize(AbstractDeserializer.java:274)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer._deserializeFromArray(CollectionDeserializer.java:359)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:272)
        //       at com.fasterxml.jackson.databind.deser.std.CollectionDeserializer.deserialize(CollectionDeserializer.java:28)
        //       at com.fasterxml.jackson.databind.deser.impl.SetterlessProperty.deserializeAndSet(SetterlessProperty.java:134)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:392)
        //       at com.fasterxml.jackson.databind.deser.BeanDeserializer.deserialize(BeanDeserializer.java:185)
        //       at com.fasterxml.jackson.databind.deser.DefaultDeserializationContext.readRootValue(DefaultDeserializationContext.java:323)
        //       at com.fasterxml.jackson.databind.ObjectReader._bindAndClose(ObjectReader.java:2105)
        //       at com.fasterxml.jackson.databind.ObjectReader.readValue(ObjectReader.java:1481)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:734)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:814)
        //   See https://diff.blue/R013 to resolve this issue.

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.updateSingleUser((User) any(), (Long) any())).thenReturn(new User());
        UserController userController = new UserController(userServiceImpl, mock(UserRepository.class));
        User user = new User();
        ResponseEntity<User> actualUpdateUserResult = userController.updateUser(user, 123L);
        assertEquals(user, actualUpdateUserResult.getBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
        assertEquals(200, actualUpdateUserResult.getStatusCodeValue());
        verify(userServiceImpl).updateSingleUser((User) any(), (Long) any());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).deleteById((Long) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        ResponseEntity<Map<String, Boolean>> actualDeleteUserResult = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class)))
                .deleteUser(123L);
        assertEquals(1, actualDeleteUserResult.getBody().size());
        assertTrue(actualDeleteUserResult.hasBody());
        assertEquals(200, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userRepository).findById((Long) any());
        verify(userRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteUser2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.blobus.apiExterneBlobus.exception.ResourceNotFoundException: deleted failled ,user_id 123 not found
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.lambda$deleteUser$1(UserServiceImpl.java:97)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.deleteUser(UserServiceImpl.java:97)
        //       at com.blobus.apiExterneBlobus.controllers.UserController.deleteUser(UserController.java:103)
        //   See https://diff.blue/R013 to resolve this issue.

        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).deleteById((Long) any());
        when(userRepository.findById((Long) any())).thenReturn(Optional.empty());
        (new UserController(new UserServiceImpl(userRepository, mock(AccountRepository.class)),
                mock(UserRepository.class))).deleteUser(123L);
    }

    /**
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    void testDeleteUser3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        doNothing().when(userServiceImpl).deleteUser((Long) any());
        ResponseEntity<Map<String, Boolean>> actualDeleteUserResult = (new UserController(userServiceImpl,
                mock(UserRepository.class))).deleteUser(123L);
        assertEquals(1, actualDeleteUserResult.getBody().size());
        assertTrue(actualDeleteUserResult.hasBody());
        assertEquals(200, actualDeleteUserResult.getStatusCodeValue());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        verify(userServiceImpl).deleteUser((Long) any());
    }

    @Test
    @Disabled
    void getUserProfileByMsisdn() throws Exception {

        String phoneNumber = "782654489";
        AmountDto amountDto = new AmountDto();
        amountDto.setCurrency(TransactionCurrency.XOF);
        amountDto.setValue(10000000.06);
        RequestBodyUserProfileDto dto = new RequestBodyUserProfileDto();
        dto.setUserId("65+65203");
        dto.setMsisdn("782654489");
        dto.setFirstName("Ba");
        dto.setLastName("El-seydi");
        dto.setSuspended(true);
        dto.setType(Collections.singletonList(Role.RETAILER).toString());
        dto.setBalance(amountDto);

        Mockito.when(userService.getUserProfileByMsisdn(phoneNumber)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/find/782654489")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msisdn", Matchers.is("782654489")));

    }

    /**
     * Method under test: {@link UserController#getUserProfileByMsisdn(String)}
     */
    @Test
    void testGetUserProfileByMsisdn() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.getAccountByPhoneNumber((String) any())).thenReturn(Optional.of(new Account()));
        ResponseEntity<RequestBodyUserProfileDto> actualUserProfileByMsisdn = (new UserController(
                new UserServiceImpl(mock(UserRepository.class), accountRepository), mock(UserRepository.class)))
                .getUserProfileByMsisdn("4105551212");
        assertTrue(actualUserProfileByMsisdn.hasBody());
        assertTrue(actualUserProfileByMsisdn.getHeaders().isEmpty());
        assertEquals(200, actualUserProfileByMsisdn.getStatusCodeValue());
        RequestBodyUserProfileDto body = actualUserProfileByMsisdn.getBody();
        assertFalse(body.isSuspended());
        assertNull(body.getMsisdn());
        AmountDto balance = body.getBalance();
        assertEquals(TransactionCurrency.XOF, balance.getCurrency());
        assertEquals(0.0d, balance.getValue().doubleValue());
        verify(accountRepository).getAccountByPhoneNumber((String) any());
    }

    /**
     * Method under test: {@link UserController#getUserProfileByMsisdn(String)}
     */
    @Test
    void testGetUserProfileByMsisdn2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        AccountRepository accountRepository = mock(AccountRepository.class);
        Customer customer = new Customer("jane.doe@example.org");
        User retailer = new User();
        ArrayList<Transaction> customerTransactions = new ArrayList<>();
        when(accountRepository.getAccountByPhoneNumber((String) any()))
                .thenReturn(Optional.of(new Account(123L, 10.0d, "Encrypted Pin Code", WalletType.BONUS, "4105551212", true,
                        customer, retailer, customerTransactions, new ArrayList<>())));
        ResponseEntity<RequestBodyUserProfileDto> actualUserProfileByMsisdn = (new UserController(
                new UserServiceImpl(mock(UserRepository.class), accountRepository), mock(UserRepository.class)))
                .getUserProfileByMsisdn("4105551212");
        assertTrue(actualUserProfileByMsisdn.hasBody());
        assertTrue(actualUserProfileByMsisdn.getHeaders().isEmpty());
        assertEquals(200, actualUserProfileByMsisdn.getStatusCodeValue());
        RequestBodyUserProfileDto body = actualUserProfileByMsisdn.getBody();
        assertTrue(body.isSuspended());
        assertNull(body.getLastName());
        assertNull(body.getFirstName());
        assertEquals("4105551212", body.getMsisdn());
        assertEquals("CUSTOMER", body.getType());
        AmountDto balance = body.getBalance();
        assertEquals(TransactionCurrency.XOF, balance.getCurrency());
        assertEquals(10.0d, balance.getValue().doubleValue());
        verify(accountRepository).getAccountByPhoneNumber((String) any());
    }

    /**
     * Method under test: {@link UserController#getUserProfileByMsisdn(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetUserProfileByMsisdn3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.blobus.apiExterneBlobus.exception.ResourceNotFoundException: msisdn invalid
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.lambda$getUserProfileByMsisdn$2(UserServiceImpl.java:107)
        //       at java.util.Optional.orElseThrow(Optional.java:403)
        //       at com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.getUserProfileByMsisdn(UserServiceImpl.java:106)
        //       at com.blobus.apiExterneBlobus.controllers.UserController.getUserProfileByMsisdn(UserController.java:50)
        //   See https://diff.blue/R013 to resolve this issue.

        AccountRepository accountRepository = mock(AccountRepository.class);
        when(accountRepository.getAccountByPhoneNumber((String) any())).thenReturn(Optional.empty());
        (new UserController(new UserServiceImpl(mock(UserRepository.class), accountRepository),
                mock(UserRepository.class))).getUserProfileByMsisdn("4105551212");
    }

    /**
     * Method under test: {@link UserController#getUserProfileByMsisdn(String)}
     */
    @Test
    void testGetUserProfileByMsisdn4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        RequestBodyUserProfileDto requestBodyUserProfileDto = new RequestBodyUserProfileDto();
        requestBodyUserProfileDto.setBalance(new AmountDto());
        requestBodyUserProfileDto.setFirstName("Jane");
        requestBodyUserProfileDto.setLastName("Doe");
        requestBodyUserProfileDto.setMsisdn("Msisdn");
        requestBodyUserProfileDto.setSuspended(true);
        requestBodyUserProfileDto.setType("Type");
        requestBodyUserProfileDto.setUserId("42");
        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.getUserProfileByMsisdn((String) any())).thenReturn(requestBodyUserProfileDto);
        ResponseEntity<RequestBodyUserProfileDto> actualUserProfileByMsisdn = (new UserController(userServiceImpl,
                mock(UserRepository.class))).getUserProfileByMsisdn("4105551212");
        assertTrue(actualUserProfileByMsisdn.hasBody());
        assertTrue(actualUserProfileByMsisdn.getHeaders().isEmpty());
        assertEquals(200, actualUserProfileByMsisdn.getStatusCodeValue());
        verify(userServiceImpl).getUserProfileByMsisdn((String) any());
    }

    /**
     * Method under test: {@link UserController#getOne(Long)}
     */
    @Test
    void testGetOne() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById((Long) any())).thenReturn(Optional.of(new User()));
        ResponseEntity<Optional<User>> actualOne = (new UserController(
                new UserServiceImpl(userRepository, mock(AccountRepository.class)), mock(UserRepository.class))).getOne(123L);
        assertTrue(actualOne.getBody().isPresent());
        assertTrue(actualOne.getHeaders().isEmpty());
        assertEquals(200, actualOne.getStatusCodeValue());
        verify(userRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link UserController#getOne(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetOne2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl.getOneUser(java.lang.Long)" because "this.userService" is null
        //       at com.blobus.apiExterneBlobus.controllers.UserController.getOne(UserController.java:60)
        //   See https://diff.blue/R013 to resolve this issue.

        (new UserController(null, mock(UserRepository.class))).getOne(123L);
    }

    /**
     * Method under test: {@link UserController#getOne(Long)}
     */
    @Test
    void testGetOne3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        UserServiceImpl userServiceImpl = mock(UserServiceImpl.class);
        when(userServiceImpl.getOneUser((Long) any())).thenReturn(Optional.of(new User()));
        ResponseEntity<Optional<User>> actualOne = (new UserController(userServiceImpl, mock(UserRepository.class)))
                .getOne(123L);
        assertTrue(actualOne.getBody().isPresent());
        assertTrue(actualOne.getHeaders().isEmpty());
        assertEquals(200, actualOne.getStatusCodeValue());
        verify(userServiceImpl).getOneUser((Long) any());
    }

    @Test
    void getOne() throws Exception {
        Mockito.when(userService.getOneUser(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("El Seydi")));
    }


    @Test
    void addUser() throws Exception {

        User user = new User(
                4l,
                "Rokhya",
                "Ndiaye",
                "rokhya-ndiaye@avimtoo.com",
                Role.RETAILER,
                "vimto1245",
                "768954362",
                "fzivbedfegjd",
                "fohfgfyf78");
        Mockito.when(userService.addSingleUser(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockResquest = MockMvcRequestBuilders.post("/api/ewallet/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user));
        mockMvc.perform(mockResquest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Rokhya")));


    }

    @Test
    void updateUserSuccess() throws Exception {
        User user = new User(
                4l,
                "Maguette",
                "Ngom",
                "rokhya-ndiaye@avimtoo.com",
                Role.RETAILER,
                "vimto1245",
                "768954362",
                "fzivbedfegjd",
                "fohfgfyf78");

        Mockito.when(userService.updateSingleUser(user, user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder moockRequest = MockMvcRequestBuilders.put("/api/ewallet/v1/users/4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user));

        mockMvc.perform(moockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.lastName", Matchers.is("Ngom")));
    }


    @Test
    void deleteUser() throws Exception {
        // test deleted methode
        Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ewallet/v1/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}