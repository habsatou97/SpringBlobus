package com.blobus.apiExterneBlobus.services.interfaces;

import com.blobus.apiExterneBlobus.models.Customer;

import java.util.List;

public interface CustomerService {
    public Customer findOne(Long id);
    public List<Customer> findAll();
    public Customer save(Customer customer);
    public Customer edit(Customer customer);
    public void delete(Long id);
}
