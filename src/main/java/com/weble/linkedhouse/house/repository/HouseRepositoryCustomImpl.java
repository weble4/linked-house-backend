package com.weble.linkedhouse.house.repository;

import static com.weble.linkedhouse.house.entity.QHouse.house;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weble.linkedhouse.house.dto.response.HouseSearchResponseDTO;
import com.weble.linkedhouse.house.entity.House;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HouseRepositoryCustomImpl implements HouseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public HouseRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 완료
    @Override
    public List<HouseSearchResponseDTO> findByCondition(String location, Integer price, Integer maxCapacity) {

        List<HouseSearchResponseDTO> responseDTOS = jpaQueryFactory
                .select(house)
                .where(house.location.like(location) // location = house.location(%location%)
                        , house.price.loe(price) // price <= house.price
                        , house.maxCapacity.loe(maxCapacity))
                .fetch()
                .stream()
                .map(HouseSearchResponseDTO::from)
                .collect(Collectors.toList());

        return responseDTOS;
    }

}
