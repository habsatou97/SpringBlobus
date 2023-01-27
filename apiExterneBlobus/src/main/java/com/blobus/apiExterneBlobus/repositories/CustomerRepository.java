package com.blobus.apiExterneBlobus.repositories;

import com.blobus.apiExterneBlobus.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
