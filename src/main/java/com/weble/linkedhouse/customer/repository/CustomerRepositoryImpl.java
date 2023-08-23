package com.weble.linkedhouse.customer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.customer.entity.Customer;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.weble.linkedhouse.customer.entity.QCustomer.customer;

@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Customer> findByCustomerEmailWithProfile(String customerEmail) {
        Customer result = queryFactory
                .selectFrom(customer)
                .leftJoin(customer.customerProfile).fetchJoin()
                .leftJoin(customer.role).fetchJoin()
                .where(customer.customerEmail.eq(customerEmail))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Customer> findByIdWithProfile(Long customerId) {
        Customer result = queryFactory
                .selectFrom(customer)
                .leftJoin(customer.customerProfile).fetchJoin()
                .leftJoin(customer.role).fetchJoin()
                .where(customer.customerId.eq(customerId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
