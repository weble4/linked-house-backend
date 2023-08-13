package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.house.entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class HouseSpecification {

    public static Specification<House> equalLocation(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location"), "%" + location + "%");
    }

    public static Specification<House> equalPrice(Integer price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("price"), price);
    }

    public static Specification<House> equalMaxCapacity(Integer maxCapacity) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("maxCapacity"), maxCapacity);
    }

}
