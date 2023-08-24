package com.weble.linkedhouse.house.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.customer.entity.QCustomer;
import com.weble.linkedhouse.customer.entity.QCustomerProfile;
import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.weble.linkedhouse.customer.entity.QCustomer.*;
import static com.weble.linkedhouse.customer.entity.QCustomerProfile.*;
import static com.weble.linkedhouse.house.entity.QHouse.house;
import static com.weble.linkedhouse.house.entity.QHouseImage.houseImage;

@RequiredArgsConstructor
public class HouseRepositoryCustomImpl implements HouseRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<House> findByIdWithCustomer(Long rentalId) {
        return Optional.ofNullable(queryFactory.selectFrom(house)
                .leftJoin(house.imagePath, houseImage)
                .leftJoin(house.customer, customer).fetchJoin()
                .leftJoin(customer.role).fetchJoin()
                .leftJoin(customer.customerProfile, customerProfile).fetchJoin()
                .where(house.rentalId.eq(rentalId))
                .fetchOne());
    }

    @Override
    public Page<House> findAllHouse(FilterKeyword filterKeyword, SearchKeyword searchKeyword, Pageable pageable) {
        List<House> content = queryFactory
                .selectFrom(house)
                .leftJoin(house.imagePath, houseImage).fetchJoin()
                .where(locationFilterEq(filterKeyword),
                        searchKeywordEq(searchKeyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(house.count())
                .from(house)
                .where(locationFilterEq(filterKeyword),
                        searchKeywordEq(searchKeyword)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression locationFilterEq(FilterKeyword filterKeyword) {
        if (filterKeyword == null) return null;
        return house.location.eq(filterKeyword.getDescription());
    }

    private BooleanExpression searchKeywordEq(SearchKeyword searchKeyword) {
        if (searchKeyword == null) {
            return null;
        }

        BooleanExpression expression = null;

        if (searchKeyword.getMinPrice() != null) {
            expression = house.price.goe(searchKeyword.getMinPrice());
        }

        if (searchKeyword.getMaxPrice() != null) {
            BooleanExpression maxPriceExpression = house.price.loe(searchKeyword.getMaxPrice());
            expression = (expression != null) ? expression.and(maxPriceExpression) : maxPriceExpression;
        }

        if (searchKeyword.getBed() != null) {
            BooleanExpression bedExpression = house.bed.goe(searchKeyword.getBed());
            expression = (expression != null) ? expression.and(bedExpression) : bedExpression;
        }

        if (searchKeyword.getRoom() != null) {
            BooleanExpression roomExpression = house.room.goe(searchKeyword.getRoom());
            expression = (expression != null) ? expression.and(roomExpression) : roomExpression;
        }

        return expression;
    }
}
