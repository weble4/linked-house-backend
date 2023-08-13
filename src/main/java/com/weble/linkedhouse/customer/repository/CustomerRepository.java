package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

    Optional<Customer> findByCustomerEmail(String customerEmail);

}

