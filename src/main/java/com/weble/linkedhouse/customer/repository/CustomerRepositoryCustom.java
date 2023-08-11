package com.weble.linkedhouse.customer.repository;

import com.weble.linkedhouse.customer.entity.Customer;

import java.util.Optional;

public interface CustomerRepositoryCustom {

    Optional<Customer> findByCustomerEmailWithCustomerProfile(String customerEmail);
}
