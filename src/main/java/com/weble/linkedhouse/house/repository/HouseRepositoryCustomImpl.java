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
        return Optional.ofNullable(
                queryFactory.selectFrom(house)
                .leftJoin(house.imagePath, houseImage).fetchJoin()
                .leftJoin(house.customer, customer).fetchJoin()
                .leftJoin(customer.role).fetchJoin()
                .leftJoin(customer.customerProfile, customerProfile).fetchJoin()
                .where(house.rentalId.eq(rentalId))
                .fetchOne()
        );
    }

    @Override
    public Page<House> findAllHouse(String location, Integer minPrice, Integer maxPrice, Integer room, Integer bed, Pageable pageable) {
        List<House> content = queryFactory
                .selectFrom(house)
                .leftJoin(house.imagePath, houseImage).fetchJoin()
                .where(locationFilterEq(location),
                        searchKeywordEq(minPrice, maxPrice, room, bed)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(house.count())
                .from(house)
                .where(locationFilterEq(location),
                        searchKeywordEq(minPrice, maxPrice, room, bed)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression locationFilterEq(String filterKeyword) {
        if (filterKeyword == null) return null;
        return house.location.eq(filterKeyword);
    }

    private BooleanExpression searchKeywordEq(Integer minPrice, Integer maxPrice, Integer room, Integer bed) {
        BooleanExpression expression = null;

        if (minPrice != null) {
            expression = house.price.goe(minPrice);
        }

        if (maxPrice != null) {
            BooleanExpression maxPriceExpression = house.price.loe(maxPrice);
            expression = (expression != null) ? expression.and(maxPriceExpression) : maxPriceExpression;
        }

        if (bed != null) {
            BooleanExpression bedExpression = house.bed.goe(bed);
            expression = (expression != null) ? expression.and(bedExpression) : bedExpression;
        }

        if (room != null) {
            BooleanExpression roomExpression = house.room.goe(room);
            expression = (expression != null) ? expression.and(roomExpression) : roomExpression;
        }

        return expression;
    }
}
