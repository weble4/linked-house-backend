package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;

import java.util.List;

public interface HouseCustomRepository {

    List<House> findAllHouseFetchJoin();
}
