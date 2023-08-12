package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;

import java.util.List;

public interface HouseRepositoryCustom {

    List<House> findAll(String location, Integer price, Integer maxCapacity);

}
