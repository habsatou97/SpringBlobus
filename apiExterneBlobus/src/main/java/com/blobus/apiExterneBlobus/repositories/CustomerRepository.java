package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.dto.CustomerEditCreateDto;
import com.blobus.apiExterneBlobus.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
