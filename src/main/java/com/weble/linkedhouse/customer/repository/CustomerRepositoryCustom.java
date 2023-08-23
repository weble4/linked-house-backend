package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.Customer;

import java.util.Optional;

public interface CustomerRepositoryCustom {


    Optional<Customer> findByCustomerEmailWithProfile(String customerEmail);
    Optional<Customer> findByIdWithProfile(Long customerId);
}
