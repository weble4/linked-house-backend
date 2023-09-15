package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HouseRepositoryCustom {

    Page<House> findAllHouse(FilterKeyword location, SearchKeyword searchKeyword, Pageable pageable);

    Optional<House> findByIdWithCustomer(Long RentalId);
}
