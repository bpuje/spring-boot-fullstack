package com.amigoscode.repository;

import com.amigoscode.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Integer customerId);
    Optional<Customer> findCustomerByEmail(String email);

}
