package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HouseRepositoryCustom {

    Page<House> findAllHouse(String location, Integer minPrice, Integer maxPrice, Integer room, Integer bed, Pageable pageable);

    Optional<House> findByIdWithCustomer(Long RentalId);
}
