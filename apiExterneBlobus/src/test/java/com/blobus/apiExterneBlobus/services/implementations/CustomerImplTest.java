package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@RunWith(SpringRunner.class)
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class CustomerImplTest {

    @Mock
    private CustomerRepository repository;
    private AutoCloseable closeable;

    //@Autowired
    //private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerImpl service;

    @BeforeEach
    void initService() {
        closeable = MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void findOne() {
        Customer customer = new Customer();
        customer.setFirstName("Ablaye");
        customer.setLastName("Faye");
        customer.setEmail("laye@gmail.com");
        customer.setId(1L);
        customer.setPhoneNumber("775545254");


        when(repository.save(any(Customer.class))).then(returnsFirstArg());

        // when
        Customer savedOrder = service.save(customer);
        // verify
        assertThat(savedOrder).isNotNull();

    }

    @Test
    void findAll() {
        List<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer();
        customer1.setFirstName("Ablaye");
        customer1.setLastName("Faye");
        customer1.setEmail("laye@gmail.com");
        customer1.setId(1L);
        customer1.setPhoneNumber("775545254");

        Customer customer2 = new Customer();
        customer2.setFirstName("Ablaye");
        customer2.setLastName("Faye");
        customer2.setEmail("layefaye@gmail.com");
        customer2.setId(2L);
        customer2.setPhoneNumber("7755452544");


        customers.add(customer1);
        customers.add(customer2);


        when(repository.findAll()).thenReturn(customers);
        List<Customer> customerList = service.findAll();
        assertNotNull(customerList);
        assertEquals(2, customerList.size());
    }

    @Test
    void save() {

        Customer customer = new Customer();
        customer.setFirstName("Ablaye");
        customer.setLastName("Faye");
        customer.setEmail("laye@gmail.com");
        customer.setId(1L);
        customer.setPhoneNumber("775545254");
        when(repository.save(any(Customer.class))).then(returnsFirstArg());

        Customer customer1 = service.save(customer);

        assertNotNull(customer1.getEmail());
    }

    @Test
    void edit() {
        Customer customer = new Customer();
        customer.setFirstName("Ablaye");
        customer.setLastName("Faye");
        customer.setEmail("laye@gmail.com");
        customer.setId(1L);
        customer.setPhoneNumber("775545254");
        Customer found = service.edit(customer);

        assertNotNull(found);
        assertEquals(customer.getEmail(), found.getEmail());
        assertEquals(customer.getId(), found.getId());
    }

    @Test
    void delete() {
    }
}