package com.weble.linkedhouse.customer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.weble.linkedhouse.admin.service.AdminFilter;
import com.weble.linkedhouse.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerRepositoryCustom {

    Optional<Customer> findByCustomerEmailWithProfile(String customerEmail);
    Optional<Customer> findByIdWithProfile(Long customerId);
    Page<Customer> findAllCustomers(AdminFilter adminFilter, Pageable pageable);
}
