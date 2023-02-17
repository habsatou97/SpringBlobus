package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.CustomerEditCreateDto;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.models.User;
import com.blobus.apiExterneBlobus.repositories.UserRepository;
import com.blobus.apiExterneBlobus.services.interfaces.CustomerService;
import com.blobus.apiExterneBlobus.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/customers/")
public class CustomerController {
    private final CustomerService customerService;

    /**
     * return list of customers
     * @return ResponseEntity<List<Customer>>
     */
    @GetMapping
    public ResponseEntity<List<CustomerEditCreateDto>> findAll(){
        return ResponseEntity.ok().body(customerService.findAllDto());
    }

    /**
     * return single customer
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerEditCreateDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findOneDto(id));
    }

    /**
     * delete a specific customer
     * @param id
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,Boolean>> delete(@PathVariable Long id){
        Map<String,Boolean> response = new HashMap<>();
        customerService.delete(id);
        response.put("deleted",true);
        return ResponseEntity.ok(response);
    }

    /**
     * save a single customer
     * @param customer
     * @return
     */
    @PostMapping
    public ResponseEntity<CustomerEditCreateDto> save(@RequestBody Customer customer){
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
