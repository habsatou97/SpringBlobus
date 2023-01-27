package com.blobus.apiExterneBlobus.controllers;

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


    @GetMapping("all")
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok().body(customerService.findAll());
    }

    @GetMapping("findOne/{id}")
    public ResponseEntity<Customer> findOne(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findOne(id));
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable Long id){
        customerService.delete(id);
    }

    @PostMapping("save")
    public ResponseEntity<Customer> save(@RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.save(customer));
    }

    @PutMapping("edit")
    public ResponseEntity<Customer> edit(@RequestBody Customer customer){
        return ResponseEntity.ok().body(customerService.edit(customer));
    }

}
