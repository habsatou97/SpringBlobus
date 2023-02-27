package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.CustomerEditCreateDto;
import com.blobus.apiexterneblobus.dto.CustomerReturnDto;
import com.blobus.apiexterneblobus.models.Customer;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ablaye faye
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/admin/customers/")
public class CustomerController {
    private final CustomerService customerService;
    private final UserRepository userRepository;
    /**
     * return list of customers
     * @return ResponseEntity<List<Customer>>
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Secured("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerEditCreateDto>> findAll(){
        return ResponseEntity.ok().body(customerService.findAllDto());
    }

    /**
     * return single customer
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerReturnDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findOneDto(id));
    }

    /**
     * delete a specific customer
     * @param id
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable Long id){
        // create a map for the response
        Map<String,Boolean> response = new HashMap<>();
        // delete the customer
        customerService.delete(id);
        response.put("deleted",true);
        // return the response
        return ResponseEntity.ok(response);
    }

    /**
     * save a single customer
     * @param customer
     * @return
     */
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerReturnDto> save(@RequestBody CustomerEditCreateDto customer) throws IllegalArgumentException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return ResponseEntity.ok().body(customerService.saveDto(customer));
    }

    /**
     * edit a single customer
     * @param id
     * @param customer
     * @return
     */
    @PutMapping("{id}")
    public ResponseEntity<CustomerEditCreateDto> edit(@PathVariable Long id,@RequestBody Customer customer){

        return ResponseEntity.ok().body(customerService.editDto(id,customer));
    }

}
