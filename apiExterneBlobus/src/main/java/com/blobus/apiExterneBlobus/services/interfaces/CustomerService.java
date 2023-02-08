package com.blobus.apiExterneBlobus.services.interfaces;

import com.blobus.apiExterneBlobus.dto.CustomerEditCreateDto;
import com.blobus.apiExterneBlobus.models.Customer;

import java.util.List;

public interface CustomerService {
    public Customer findOne(Long id);
    public CustomerEditCreateDto findOneDto(Long id);
    public List<Customer> findAll();
    public List<CustomerEditCreateDto> findAllDto();
    public Customer save(Customer customer);
    public CustomerEditCreateDto saveDto(Customer customer);
    public CustomerEditCreateDto editDto(Customer customer);
    public Customer edit(Customer customer);
    public void delete(Long id);
}
