package com.blobus.apiexterneblobus.services.implementations;

import com.blobus.apiexterneblobus.dto.*;
import com.blobus.apiexterneblobus.exception.EmailAlreadyExistException;
import com.blobus.apiexterneblobus.exception.ResourceNotFoundException;
import com.blobus.apiexterneblobus.models.Account;
import com.blobus.apiexterneblobus.models.Customer;
import com.blobus.apiexterneblobus.repositories.CustomerRepository;
import com.blobus.apiexterneblobus.services.interfaces.AccountService;
import com.blobus.apiexterneblobus.services.interfaces.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Ablaye Faye
 * Customer Implementation that implements Customer Service
 */
@RequiredArgsConstructor
@Service
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    private final KeyGeneratorImpl keyGenerator;

    /**
     * find single customer DTO
     * @param id
     * @return
     */
    @Override
    public CustomerEditCreateDto findOneDto(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer with id"+": "+id+ " don't exist"));
        CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
        customerEditCreateDto.setEmail(customer.getEmail());
        customerEditCreateDto.setFirstName(customer.getFirstName());
        customerEditCreateDto.setPhoneNumber(customer.getPhoneNumber());
        customerEditCreateDto.setLastName(customer.getLastName());
        return customerEditCreateDto;
    }


    /**
     * find single customer
     * @param id
     * @return
     */
    @Override
    public Customer findOne(Long id) {
        return customerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer with id"+": "+id+ " don't exist"));
    }

    /**
     * find all customer
     * @return
     */
    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    /**
     * find all customer DTO
     * @return
     */
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
        if (customer.getPhoneNumber().length() != 9 ) throw new IllegalStateException("The phone number cannot be more than 9 characters.");
        if (
                customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getEmail().isEmpty()
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number cannot be empty.");

        return customerRepository.save(customer);
    }

    public CustomerEditCreateDto saveDto(CustomerEditCreateDto customer) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        if (!findByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("This email is taken.");
        }
        if (!findByPhoneNumber(customer.getPhoneNumber()))
            throw new IllegalArgumentException("This phone number is taken.");
        if (customer.getPhoneNumber().length() != 9 ) throw new IllegalStateException("The phone number cannot be more than 9 characters.");
        if (
                customer.getFirstName().isEmpty() ||
                customer.getLastName().isEmpty() ||
                customer.getEmail().isEmpty()
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number cannot be empty.");
        CustomerEditCreateDto customerEditCreateDto = new CustomerEditCreateDto();
        customerEditCreateDto.setEmail(customer.getEmail());
        customerEditCreateDto.setFirstName(customer.getFirstName());
        customerEditCreateDto.setPhoneNumber(customer.getPhoneNumber());
        customerEditCreateDto.setLastName(customer.getLastName());

        Customer customer1 = Customer.builder()
                .email(customerEditCreateDto.getEmail())
                .firstName(customerEditCreateDto.getFirstName())
                        .phoneNumber(customerEditCreateDto.getPhoneNumber())
                                .lastName(customerEditCreateDto.getLastName()).build();
        Customer customer2 = customerRepository.save(customer1);

        if (!customer.getTransferAccounts().isEmpty()){
            for (CreateOrEditAccountDto account : customer.getTransferAccounts()
            ) {
                CreateAccountDto accountDto = CreateAccountDto.builder().walletType(account.getWalletType())
                        .encryptedPinCode(keyGenerator.encrypt(new DecryptDto(account.getEncryptedPinCode())))
                        .phoneNumber(account.getPhoneNumber()).build();
                System.out.println(accountDto);
                accountService.createCustomerTransfertAccount(accountDto, customer2.getId());
            }
        }
        List<CreateOrEditAccountDto> accounts =  customer.getTransferAccounts().stream().map(account -> {
            CreateOrEditAccountDto createOrEditAccountDto = CreateOrEditAccountDto.builder().walletType(account.getWalletType()).balance(account.getBalance()).encryptedPinCode(account.getEncryptedPinCode()).phoneNumber(account.getPhoneNumber()).build();
            return  CreateOrEditAccountDto.builder().walletType(account.getWalletType()).balance(account.getBalance()).encryptedPinCode(account.getEncryptedPinCode()).phoneNumber(account.getPhoneNumber()).build();
        }).toList();
        customer.setTransferAccounts(accounts);

        customerEditCreateDto.setTransferAccounts(customer.getTransferAccounts());

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
        ) throw new IllegalStateException("Firstname, lastname, email or phone Number cannot be empty.");


        Customer customer1 = customerRepository.findById(id).orElseThrow();
        if (!customer.getPhoneNumber().equals(customer1.getPhoneNumber())){
            if (!findByPhoneNumber(customer.getPhoneNumber()))
                throw new IllegalArgumentException("This phone number is taken.");
        }
        if (!customer.getEmail().equals(customer1.getEmail())){
            if (!findByEmail(customer.getEmail()))
                throw new EmailAlreadyExistException("Email"+ customer.getEmail() +" is taken.");
        }
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
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            customerRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Customer with id "+id+" doesn't exists");
        }

    }

    @Override
    public boolean findByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer.isEmpty();
    }

    @Override
    public boolean findByPhoneNumber(String phoneNumber) {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(phoneNumber);
        return customer.isEmpty();
    }
}
