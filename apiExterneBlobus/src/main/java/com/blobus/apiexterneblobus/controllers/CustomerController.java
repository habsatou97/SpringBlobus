package com.blobus.apiexterneblobus.controllers;

import com.blobus.apiexterneblobus.dto.CustomerEditCreateDto;
import com.blobus.apiexterneblobus.dto.CustomerReturnDto;
import com.blobus.apiexterneblobus.models.Customer;
import com.blobus.apiexterneblobus.repositories.UserRepository;
import com.blobus.apiexterneblobus.services.interfaces.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "This operation allows to list all the  registred customers")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Secured("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerEditCreateDto>> findAll(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(customerService.findAllDto());
    }

    /**
     * return single customer
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @Operation(summary = "This operation allows to get a customers by his ID")
    public ResponseEntity<CustomerReturnDto> findOne(@Parameter(description = "The ID is required")@PathVariable Long id,@RequestHeader("Authorization") String token){
        return ResponseEntity.ok().body(customerService.findOneDto(id));
    }

    /**
     * delete a specific customer
     * @param id
     */
    @Operation(summary = "This operation allows to delete a customer by his ID")
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@Parameter(description = "The ID is required") @PathVariable Long id, @RequestHeader("Authorization") String token){
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
    @Operation(summary = "This operation allows to save a new customer")
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerReturnDto> save(@RequestBody(required = true) CustomerEditCreateDto customer,@RequestHeader("Authorization") String token) throws IllegalArgumentException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, InvalidKeySpecException, InvalidKeyException {
        return ResponseEntity.ok().body(customerService.saveDto(customer));
    }

    /**
     * edit a single customer
     * @param id
     * @param customer
     * @return
     */
    @Operation(summary = "This operation allows to edit a customer")
    @PutMapping("{id}")
    public ResponseEntity<CustomerEditCreateDto> edit(@PathVariable Long id,@RequestBody Customer customer,@RequestHeader("Authorization") String token){

        return ResponseEntity.ok().body(customerService.editDto(id,customer));
    }

}
