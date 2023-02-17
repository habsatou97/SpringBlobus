package com.blobus.apiexterneblobus.services.interfaces;
import com.blobus.apiexterneblobus.dto.CustomerEditCreateDto;
import com.blobus.apiexterneblobus.models.Customer;
import java.util.List;

/**
 * @author Ablaye Faye
 */
public interface CustomerService {
    public Customer findOne(Long id);
    public CustomerEditCreateDto findOneDto(Long id);
    public List<Customer> findAll();
    public List<CustomerEditCreateDto> findAllDto();
    public Customer save(Customer customer);
    public CustomerEditCreateDto saveDto(Customer customer);
    public CustomerEditCreateDto editDto(Long id,Customer customer);
    public Customer edit(Customer customer);
    public void delete(Long id);
    public boolean findByEmail(String email);
    public boolean findByPhoneNumber(String phoneNumber);
}
