package com.blobus.apiExterneBlobus.controllers;

import com.blobus.apiExterneBlobus.dto.CustomerEditCreateDto;
import com.blobus.apiExterneBlobus.models.Customer;
import com.blobus.apiExterneBlobus.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ewallet/v1/customers/")
public class CustomerController {
    private final CustomerService customerService;


    @GetMapping
    public ResponseEntity<List<CustomerEditCreateDto>> findAll(){
        return ResponseEntity.ok().body(customerService.findAllDto());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerEditCreateDto> findOne(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findOneDto(id));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        customerService.delete(id);
    }

    @PostMapping
    public ResponseEntity<CustomerEditCreateDto> save(@RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.saveDto(customer));
    }

    @PutMapping
    public ResponseEntity<CustomerEditCreateDto> edit(@RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.editDto(customer));
    }

}
