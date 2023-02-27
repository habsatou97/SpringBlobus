package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.config.JwtAuthenticationFilter;
import com.blobus.apiexterneblobus.config.JwtService;
import com.blobus.apiexterneblobus.config.SecurityConfiguration;
import com.blobus.apiexterneblobus.dto.AmountDto;
import com.blobus.apiexterneblobus.dto.RequestBodyUserProfileDto;
import com.blobus.apiexterneblobus.dto.WalletTypeDto;
import com.blobus.apiexterneblobus.models.enums.Role;
import com.blobus.apiexterneblobus.models.enums.TransactionCurrency;
import com.blobus.apiexterneblobus.models.enums.WalletType;
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class GetProfileByMsisdnControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    AccountServiceImpl accountService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    UserController userController;
    @MockBean
    JwtService jwtService;
    @MockBean
    SecurityConfiguration securityConfiguration;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

        WalletTypeDto walletTypeDto= new WalletTypeDto();
        walletTypeDto.setWalletType(WalletType.BONUS);

        Mockito.when(accountService.getUserProfileByMsisdn(phoneNumber,WalletType.BONUS)).thenReturn(dto);
        org.assertj.core.api.Assertions.assertThat(accountService.getUserProfileByMsisdn(phoneNumber,WalletType.BONUS)).isNotNull();

    }
}