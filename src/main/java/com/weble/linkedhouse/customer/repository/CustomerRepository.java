package com.weble.linkedhouse.customer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.weble.linkedhouse.admin.service.AdminFilter;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.QCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {

    Optional<Customer> findByCustomerEmail(String customerEmail);

    Optional<Customer> findByCustomer(Long customerId);
    BooleanExpression adminFilterEq(QCustomer customer, AdminFilter adminFilter);
}


