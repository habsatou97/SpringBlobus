package com.blobus.apiExterneBlobus.repositories;
import com.blobus.apiExterneBlobus.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * @author Ablaye Faye
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
