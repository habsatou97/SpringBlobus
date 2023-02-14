package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.config.JwtAuthenticationFilter;
import com.blobus.apiExterneBlobus.config.JwtService;
import com.blobus.apiExterneBlobus.config.SecurityConfiguration;
import com.blobus.apiExterneBlobus.dto.AmountDto;
import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.dto.UserDto;
import com.blobus.apiExterneBlobus.dto.UserWithNineaDto;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
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
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()));
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
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/retailers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Cheikh Yangkhouba")));

    }

    @Test
    void getUserProfileByMsisdn() throws Exception {

        String phoneNumber = "782654489";
        AmountDto amountDto= new AmountDto();
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
                .andExpect(jsonPath("$",notNullValue()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.msisdn", Matchers.is("782654489")));

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

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testAddUser(){
        UserRepository userRepository1= mock(UserRepository.class);
        AccountRepository accountRepository1= mock(AccountRepository.class);
        UserServiceImpl userService1 = mock(UserServiceImpl.class);
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

        UserController userController= new UserController(new UserServiceImpl(userRepository1,accountRepository1), userRepository1);

                UserWithNineaDto user=UserWithNineaDto.builder()
                        .ninea("vbfggtrt")
                        .firstName("Rokhya")
                        .lastName("Ndiaye")
                        .email("rokhya-ndiaye@avimtoo.com")
                        .phoneNumber("768954362")
                        .roles(Collections.singletonList(Role.RETAILER))
                        .build();

            ResponseEntity<UserDto>  userResult = userController.addUser(user);
            assertTrue(userResult.getHeaders().isEmpty());
            assertEquals(200,userResult.getStatusCodeValue());
            verify(userRepository1).save((User) any());

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
     mockMvc.perform(MockMvcRequestBuilders.delete("/api/ewallet/v1/users/3").with(csrf())
             .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk());
    }

}