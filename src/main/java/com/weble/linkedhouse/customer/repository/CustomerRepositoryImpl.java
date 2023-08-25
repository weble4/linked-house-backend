package com.weble.linkedhouse.customer.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.admin.service.AdminFilter;
import com.weble.linkedhouse.customer.entity.Customer;

import com.weble.linkedhouse.customer.entity.QCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.weble.linkedhouse.customer.entity.QCustomer.customer;


@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

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


    public Page<Customer> findAllCustomers(AdminFilter adminFilter, Pageable pageable) {

        List<Customer> content = queryFactory
                .selectFrom(customer)
                .leftJoin(customer.customerProfile).fetchJoin()
                .where()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(customer.count())
                .from(customer)
                .where();

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
