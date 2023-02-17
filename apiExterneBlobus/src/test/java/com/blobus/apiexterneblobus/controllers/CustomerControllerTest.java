package com.blobus.apiexterneblobus.controllers;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blobus.apiexterneblobus.dto.CustomerEditCreateDto;
import com.blobus.apiexterneblobus.models.Customer;
import com.blobus.apiexterneblobus.repositories.CustomerRepository;
import com.blobus.apiexterneblobus.services.implementations.CustomerImpl;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {
    @Test
    void testFindAll() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findAll()).thenReturn(List.of(Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh1@gmail.com")
                .phoneNumber("778545382")
                .build(), Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh2@gmail.com")
                .phoneNumber("778545383")
                .build()));
        ResponseEntity<List<CustomerEditCreateDto>> actualFindAllResult = (new CustomerController(new CustomerImpl(customerRepository)))
                .findAll();
        assertTrue(actualFindAllResult.hasBody());
        assertEquals(200, actualFindAllResult.getStatusCodeValue());
        assertTrue(actualFindAllResult.getHeaders().isEmpty());
        assertEquals(2, Objects.requireNonNull(actualFindAllResult.getBody()).size());
        verify(customerRepository).findAll();
    }


    @Test
    void testFindOne() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((Long) any())).thenReturn(Optional.of(new Customer()));
        ResponseEntity<CustomerEditCreateDto> actualFindOneResult = (new CustomerController(new CustomerImpl(customerRepository)))
                .findOne(123L);
        assertTrue(actualFindOneResult.hasBody());
        assertTrue(actualFindOneResult.getHeaders().isEmpty());
        assertEquals(200, actualFindOneResult.getStatusCodeValue());
        verify(customerRepository).findById((Long) any());
    }


    @Test
    void testDelete() {
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        doNothing().when(customerRepository).deleteById((Long) any());

    }


    @Test
    void testSave() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository));
        Customer customer = new Customer();
        customer.setEmail("ablaye@gmail.com");
        customer.setLastName("Faye");
        customer.setFirstName("hello");
        customer.setPhoneNumber("123456789");
        ResponseEntity<CustomerEditCreateDto> actualSaveResult = customerController.save(customer);
        assertEquals(customer.getEmail(), Objects.requireNonNull(actualSaveResult.getBody()).getEmail());
        assertTrue(actualSaveResult.getHeaders().isEmpty());
        assertEquals(200, actualSaveResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());
    }


    @Test
    void testEdit() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository));
        Customer customer = new Customer();
        customer.setEmail("ablaye@gmail.com");
        customer.setLastName("Faye");
        customer.setFirstName("hello");
        customer.setPhoneNumber("123456789");
        ResponseEntity<CustomerEditCreateDto> actualSaveResult = customerController.save(customer);
        assertEquals(customer.getEmail(), Objects.requireNonNull(actualSaveResult.getBody()).getEmail());
        assertTrue(actualSaveResult.getHeaders().isEmpty());
        assertEquals(200, actualSaveResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());

    }


}

