package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.AmountDto;
import com.blobus.apiExterneBlobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiExterneBlobus.exception.ResourceNotFoundException;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.models.enums.Role;
import com.blobus.apiExterneBlobus.models.enums.TransactionCurrency;
import com.blobus.apiExterneBlobus.repositories.AccountRepository;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.implementations.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
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

    @Test
    void getAllUsers() throws Exception {

        List<User> users= new ArrayList<>(Arrays.asList(user1,user2));
        List<User> users1= userRepository.findAll();
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Cheikh Yangkhouba")));
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
        Mockito.when(userService.getAllRetailer()).thenReturn(userList);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/ewallet/v1/users/retailers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("Cheikh Yangkhouba")));

    }

    @Test
    @Disabled
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

        User user= new  User(
                4l,
                "Rokhya",
                "Ndiaye",
                "rokhya-ndiaye@avimtoo.com" ,
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
                .andExpect(MockMvcResultMatchers.jsonPath("$",notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Rokhya")));


    }

    @Test
    void updateUserSuccess() throws Exception {
        User user= new  User(
                4l,
                "Maguette",
                "Ngom",
                "rokhya-ndiaye@avimtoo.com" ,
                Role.RETAILER,
                "vimto1245",
                "768954362",
                "fzivbedfegjd",
                "fohfgfyf78");

        Mockito.when(userService.updateSingleUser(user,user.getId())).thenReturn(user);

        MockHttpServletRequestBuilder moockRequest=MockMvcRequestBuilders.put("/api/ewallet/v1/users/4")
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

     Mockito.when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
     mockMvc.perform(MockMvcRequestBuilders.delete("/api/ewallet/v1/users/3")
             .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk());
    }

}