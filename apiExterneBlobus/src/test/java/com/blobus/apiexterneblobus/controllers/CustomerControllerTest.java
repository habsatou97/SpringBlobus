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
import com.blobus.apiexterneblobus.services.implementations.AccountServiceImpl;
import com.blobus.apiexterneblobus.services.implementations.CustomerImpl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.blobus.apiexterneblobus.services.implementations.KeyGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class CustomerControllerTest {
    @Test
    void testFindAll() {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        AccountServiceImpl accountService = mock(AccountServiceImpl.class);
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
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
        ResponseEntity<List<CustomerEditCreateDto>> actualFindAllResult = (new CustomerController(new CustomerImpl(customerRepository,accountService,keyGenerator)))
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
        AccountServiceImpl accountService = mock(AccountServiceImpl.class);
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);
        when(customerRepository.findById((Long) any())).thenReturn(Optional.of(new Customer()));
        ResponseEntity<CustomerEditCreateDto> actualFindOneResult = (new CustomerController(new CustomerImpl(customerRepository,accountService,keyGenerator)))
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
    void testSave() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        AccountServiceImpl accountService = mock(AccountServiceImpl.class);
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);

        when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository,accountService,keyGenerator));
        Customer customer = new Customer();
        customer.setEmail("ablaye@gmail.com");
        customer.setLastName("Faye");
        customer.setFirstName("hello");
        customer.setPhoneNumber("123456789");
        CustomerEditCreateDto dto = new CustomerEditCreateDto();
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());

        ResponseEntity<CustomerEditCreateDto> actualSaveResult = customerController.save(dto);
        assertEquals(customer.getEmail(), Objects.requireNonNull(actualSaveResult.getBody()).getEmail());
        assertTrue(actualSaveResult.getHeaders().isEmpty());
        assertEquals(200, actualSaveResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());
    }


    @Test
    void testEdit() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        AccountServiceImpl accountService = mock(AccountServiceImpl.class);
        KeyGeneratorImpl keyGenerator = mock(KeyGeneratorImpl.class);

        when(customerRepository.save((Customer) any())).thenReturn(new Customer());
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository,accountService,keyGenerator));
        Customer customer = new Customer();
        customer.setEmail("ablaye@gmail.com");
        customer.setLastName("Faye");
        customer.setFirstName("hello");
        customer.setPhoneNumber("123456789");

        customerRepository.save(customer);

        CustomerEditCreateDto dto = new CustomerEditCreateDto();
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());

        ResponseEntity<CustomerEditCreateDto> actualSaveResult = customerController.save(dto);
        assertEquals(customer.getEmail(), Objects.requireNonNull(actualSaveResult.getBody()).getEmail());
        assertTrue(actualSaveResult.getHeaders().isEmpty());
        assertEquals(200, actualSaveResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());

    }


}

