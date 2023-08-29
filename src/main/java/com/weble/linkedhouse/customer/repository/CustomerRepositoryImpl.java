package com.weble.linkedhouse.customer.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.constant.Banneduser;
import com.weble.linkedhouse.customer.entity.constant.Role;
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

    @Override
    public Page<Customer> findAllCustomers(Banneduser banneduser, Role role, Pageable pageable) {

        List<Customer> content = queryFactory
                .selectFrom(customer)
                .leftJoin(customer.role).fetchJoin()
                .leftJoin(customer.customerProfile).fetchJoin()
                .where(userRoleFilter(role),
                        userSuspendedFilter(banneduser))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(customer.count())
                .where(userRoleFilter(role),
                        userSuspendedFilter(banneduser))
                .from(customer);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression userRoleFilter(Role role) {
        if (role == null) {
            return null;
        }
        return customer.role.contains(role);
    }

    private BooleanExpression userSuspendedFilter(Banneduser banneduser) {
        if (banneduser == null) {
            return null;
        }
        return customer.suspended.eq(banneduser);
    }
}
