package com.blobus.apiExterneBlobus.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.services.implementations.CustomerImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {
    /**
     * Method under test: {@link CustomerController#findAll()}
     */
    @Test
    void testFindAll() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

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
        ResponseEntity<List<Customer>> actualFindAllResult = (new CustomerController(new CustomerImpl(customerRepository)))
                .findAll();
        assertTrue(actualFindAllResult.hasBody());
        assertEquals(200, actualFindAllResult.getStatusCodeValue());
        assertTrue(actualFindAllResult.getHeaders().isEmpty());
        verify(customerRepository).findAll();
    }


    /**
     * Method under test: {@link CustomerController#findOne(Long)}
     */
    @Test
    void testFindOne() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((Long) any())).thenReturn(Optional.of(new Customer("jane.doe@example.org")));
        ResponseEntity<Customer> actualFindOneResult = (new CustomerController(new CustomerImpl(customerRepository)))
                .findOne(123L);
        assertTrue(actualFindOneResult.hasBody());
        assertTrue(actualFindOneResult.getHeaders().isEmpty());
        assertEquals(200, actualFindOneResult.getStatusCodeValue());
        verify(customerRepository).findById((Long) any());
    }



    /**
     * Method under test: {@link CustomerController#delete(Long)}
     */
    @Test
    void testDelete() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        Customer savedEmployee = Customer.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramesh@gmail.com")
                .phoneNumber("778545382")
                .build();
        customerRepository.save(savedEmployee);
        doNothing().when(customerRepository).deleteById(savedEmployee.getId());
        (new CustomerController(new CustomerImpl(customerRepository))).delete(savedEmployee.getId());
        verify(customerRepository).deleteById(savedEmployee.getId());
    }

    /**
     * Method under test: {@link CustomerController#delete(Long)}
     */

    /**
     * Method under test: {@link CustomerController#save(Customer)}
     */
    @Test
    void testSave() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save((Customer) any())).thenReturn(new Customer("jane.doe@example.org"));
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository));
        Customer customer = new Customer("jane.doe@example.org");
        ResponseEntity<Customer> actualSaveResult = customerController.save(customer);
        assertEquals(customer, actualSaveResult.getBody());
        assertTrue(actualSaveResult.getHeaders().isEmpty());
        assertEquals(200, actualSaveResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());
    }

    /**
     * Method under test: {@link CustomerController#edit(Customer)}
     */
    @Test
    void testEdit() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Diffblue AI was unable to find a test

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.save((Customer) any())).thenReturn(new Customer("jane.doe@example.org"));
        CustomerController customerController = new CustomerController(new CustomerImpl(customerRepository));
        Customer customer = new Customer("jane.doe@example.org");
        ResponseEntity<Customer> actualEditResult = customerController.edit(customer);
        assertEquals(customer, actualEditResult.getBody());
        assertTrue(actualEditResult.getHeaders().isEmpty());
        assertEquals(200, actualEditResult.getStatusCodeValue());
        verify(customerRepository).save((Customer) any());
    }


}

