package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.dto.response.HouseSearchResponseDTO;
import com.weble.linkedhouse.house.entity.House;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepositoryCustom {

    List<HouseSearchResponseDTO> findByCondition(String location, Integer price, Integer maxCapacity);

}
