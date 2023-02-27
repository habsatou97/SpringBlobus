package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.config.JwtAuthenticationFilter;
import com.blobus.apiexterneblobus.config.JwtService;
import com.blobus.apiexterneblobus.config.SecurityConfiguration;
import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.models.User;
import com.blobus.apiexterneblobus.models.enums.Role;
import com.blobus.apiexterneblobus.repositories.AccountRepository;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.implementations.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
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
    PasswordEncoder passwordEncoder;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    JwtAuthenticationFilter  jwtAuthenticationFilter;
    @MockBean
    JwtService jwtService;
    @MockBean
    SecurityConfiguration securityConfiguration;


    @Autowired
    private WebApplicationContext webApplicationContext;



    User user1= new  User(
            3l,
                "El Seydi",
                        "Ba",
                "em-seydi.ba@avimtoo.com" ,
                Role.RETAILER,
                "vimto1245",
                        "718954362",
                        "fzivbedfegjd",
                        "fohfgfyf78");

    User user2= new  User(
            2l,
            "Cheikh Yangkhouba",
            "Cisse",
            "cheikh-yangkhouba.cisse@avimtoo.com" ,
            Role.ADMIN,
            "vimto1245",
            "708954362",
            "fzivbeegjd",
            "fohfyf78");
    User user3= new  User(
            4l,
            "Cheikh Yangkhouba",
            "Cisse",
            "cheikh-yangkhouba.cisse@avimtoo.com" ,
            Role.RETAILER,
            "vimto1245",
            "708954362",
            "fzivbeegjd",
            "fohfyf78");

    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getAllUsers() throws Exception {

        List<User> users= new ArrayList<>(Arrays.asList(user1,user2));
        List<User> users1= userRepository.findAll();
        Mockito.when(userService.getAllUsers()).thenReturn(users1.stream().map(
               user ->{ UserDto dto = new UserDto();
                   dto.setLastName(user.getLastName());
                   dto.setEmail(user.getEmail());
                   dto.setFirstName(user.getFirstName());
                   dto.setPhoneNumber(user.getPhoneNumber());
                   return dto;
               }).toList()
        );
       verify(userRepository).findAll();
       assertThat(userService.getAllUsers()).isNotNull();
    }

    @Test
    void getAllRetailer()  throws Exception {
    List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));
    List<User> userList = new ArrayList<>();
        for (User user: users){
            if (user.getRoles().contains(Role.RETAILER)){
                userList.add(user);
            }
        }
        Mockito.when(userService.getAllRetailer()).thenReturn(userList.stream().map(
                user -> {
                    UserDto dto = new UserDto();
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setFirstName(user.getFirstName());
                    dto.setPhoneNumber(user.getPhoneNumber());
                    return dto;
                }).toList());
       assertThat(userService.getAllRetailer()).isNotNull();

    }

    @Test
    void getOne() throws Exception {
        Optional<User> users1= userRepository.findById(user1.getId());
     Mockito.when(userService.getOneUser(user1.getId())).thenReturn(
             users1.stream().map(
                     user ->{ UserDto dto = new UserDto();
                         dto.setLastName(user.getLastName());
                         dto.setEmail(user.getEmail());
                         dto.setFirstName(user.getFirstName());
                         dto.setPhoneNumber(user.getPhoneNumber());
                         return dto;
                     }).findAny()
     );

       assertThat(userService.getOneUser(user1.getId())).isNotNull();

    }

    @Test
    void testAddUser(){
        UserRepository userRepository1= mock(UserRepository.class);
        AccountRepository accountRepository1= mock(AccountRepository.class);
        UserServiceImpl userService1 = mock(UserServiceImpl.class);
        UserController controller= mock(UserController.class);
        when(userRepository1.save((User) any())).thenReturn( new  User(
                4l,
                "Rokhya",
                "Ndiaye",
                "rokhya-ndiaye@avimtoo.com" ,
                Role.RETAILER,
                "vimto1245",
                "768954362",
                "fzivbedfegjd",
                "fohfgfyf78"));

        UserController userController= new UserController(
                new UserServiceImpl(userRepository1,accountRepository1), userRepository1);

                UserWithNineaDto user=UserWithNineaDto.builder()
                        .ninea("vbfggtrt")
                        .firstName("Rokhya")
                        .lastName("Ndiaye")
                        .email("rokhya-ndiaye@avimtoo.com")
                        .phoneNumber("768954362")
                        .roles(Collections.singletonList(Role.RETAILER))
                        .build();
        when(userService1.addSingleUser(user)).thenReturn(new UserDto());
        assertThat(userService1.addSingleUser(user)).isNotNull();
    }

    @Test
    void testUpdateSingleUser(){

        UserRepository userRepository2= mock(UserRepository.class);
        UserServiceImpl userService2 = mock(UserServiceImpl.class);
        UserWithNineaDto user =UserWithNineaDto.builder()
                .ninea("vbfggtrt")
                .firstName("Rokhya")
                .lastName("Ndiaye")
                .email("rokhya-ndiaye@avimtoo.com")
                .phoneNumber("768954362")
                .roles(Collections.singletonList(Role.RETAILER))
                .build();

        when(userRepository2.save((User) any())).thenReturn(new User());

        UserController userController= new UserController(userService2, userRepository2);
        User user1= new  User(
                4l,
                "Rokhya",
                "Ndiaye",
                "rokhya-ndiaye@avimtoo.com" ,
                Role.RETAILER,
                "vimto1245",
                "768954362",
                "fzivbedfegjd",
                "fohfgfyf78");
        ResponseEntity<UserDto>  userResult = userController.updateUser(user,user1.getId());

        assertTrue(userResult.getHeaders().isEmpty());
        assertEquals(200,userResult.getStatusCodeValue());

    }

    @Test
    void deleteUser() throws Exception {

     Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
    doNothing().when(userRepository).deleteById((Long) any());
    }

}