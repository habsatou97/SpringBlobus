package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository repository;


    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setup(){
        repository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        // given - precondition or setup
        List<Customer> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Customer.builder().firstName("Ramesh").lastName("Fadatare").email("ramesh@gmail.com").phoneNumber("778885522").build());
        listOfEmployees.add(Customer.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").phoneNumber("775554499").build());
        repository.saveAll(listOfEmployees);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/ewallet/v1/customers/"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        is(listOfEmployees.size())));
    }

    @Test
    void findOne() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Customer employee = Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmaill.com")
                .phoneNumber("7785453271")
                .build();
        repository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/ewallet/v1/customers/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void delete() throws Exception {
        // given - precondition or setup
        Customer savedEmployee = Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .phoneNumber("778545382")
                .build();
        repository.save(savedEmployee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/ewallet/v1/customers/{id}", savedEmployee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void save() throws Exception {
        // given - precondition or setup
        Customer customer = Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .phoneNumber("778545382")
                .build();

        // when - action or behaviour that we are going test
        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/ewallet/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));




    }

    @Test
    void edit() throws Exception {
        // given - precondition or setup

        Customer employee = Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmaill.com")
                .phoneNumber("7785453271")
                .build();

        Customer customer = repository.save(employee);
        customer.setFirstName("Ablaye");
        customer.setTransferAccounts(new ArrayList<>());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/ewallet/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}