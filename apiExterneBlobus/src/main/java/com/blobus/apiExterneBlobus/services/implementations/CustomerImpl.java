package com.blobus.apiExterneBlobus.services.implementations;

import com.blobus.apiExterneBlobus.dto.CustomerEditCreateDto;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.repositories.CustomerRepository;
import com.blobus.apiExterneBlobus.services.interfaces.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomerEditCreateDto findOneDto(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer with id"+": "+id+ " don't exist"));
        CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
        customerEditCreateDto.setEmail(customer.getEmail());
        customerEditCreateDto.setFirstName(customer.getFirstName());
        customerEditCreateDto.setPhoneNumber(customer.getPhoneNumber());
        customerEditCreateDto.setLastName(customer.getLastName());
        return customerEditCreateDto;
    }


    @Override
    public Customer findOne(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer with id"+": "+id+ " don't exist"));
    }

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    @Override
    public List<CustomerEditCreateDto> findAllDto() {
        return customerRepository.findAll().stream().map(customer -> {
            CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
            customerEditCreateDto.setLastName(customer.getLastName());
            customerEditCreateDto.setPhoneNumber(customer.getPhoneNumber());
            customerEditCreateDto.setEmail(customer.getEmail());
            customerEditCreateDto.setFirstName(customer.getFirstName());
            return customerEditCreateDto;
        }).toList();
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getPhoneNumber().length() > 9 ) throw new IllegalStateException("The phone number cannot be more than 9 characters.");
        if (customer.getPhoneNumber().isEmpty() ||
                customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getEmail().isEmpty()
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number connot be empty.");

        return customerRepository.save(customer);
    }

    public CustomerEditCreateDto saveDto(Customer customer) {
        if (customer.getPhoneNumber().length() > 9 ) throw new IllegalStateException("The phone number cannot be more than 9 characters.");
        if (customer.getPhoneNumber().isEmpty() ||
                customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getEmail().isEmpty()
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number connot be empty.");
        CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
        customerEditCreateDto.setEmail(customer.getEmail());
        customerEditCreateDto.setFirstName(customer.getFirstName());
        customerEditCreateDto.setPhoneNumber(customer.getPhoneNumber());
        customerEditCreateDto.setLastName(customer.getLastName());
        customerRepository.save(customer);
        return customerEditCreateDto;
    }

    @Override
    public Customer edit(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public CustomerEditCreateDto editDto(Long id, Customer customer) {
        if (customer.getPhoneNumber().length() > 9 ) throw new IllegalStateException("The phone number cannot be more than 9 characters.");
        if (customer.getPhoneNumber().isEmpty() ||
                customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getEmail().isEmpty()
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number connot be empty.");


        Customer customer1 = customerRepository.findById(id).orElseThrow();
        System.out.println("*********************************************");
        System.out.println(customer.getFirstName());
        System.out.println(customer.getPhoneNumber());
        System.out.println(customer.getLastName());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customer1.setLastName(customer.getLastName());
        customer1.setEmail(customer.getEmail());
        customer1.setFirstName(customer.getFirstName());


        CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
        customerEditCreateDto.setEmail(customer1.getEmail());
        customerEditCreateDto.setFirstName(customer1.getFirstName());
        customerEditCreateDto.setPhoneNumber(customer1.getPhoneNumber());
        customerEditCreateDto.setLastName(customer1.getLastName());
        customerRepository.save(customer1);
        return customerEditCreateDto;
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
