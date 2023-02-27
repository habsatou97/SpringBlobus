package com.blobus.apiexterneblobus.services.interfaces;
import com.blobus.apiexterneblobus.dto.CustomerEditCreateDto;
import com.blobus.apiexterneblobus.dto.CustomerReturnDto;
import com.blobus.apiexterneblobus.models.Customer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author Ablaye Faye
 */
public interface CustomerService {
    public Customer findOne(Long id);
    public CustomerReturnDto findOneDto(Long id);
    public List<Customer> findAll();
    public List<CustomerEditCreateDto> findAllDto();
    public Customer save(Customer customer);
    public CustomerReturnDto saveDto(CustomerEditCreateDto customer) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException;
    public CustomerEditCreateDto editDto(Long id,Customer customer);
    public Customer edit(Customer customer);
    public void delete(Long id);
    public boolean findByEmail(String email);
    public boolean findByPhoneNumber(String phoneNumber);
}
