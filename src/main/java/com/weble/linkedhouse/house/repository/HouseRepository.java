package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByCustomerId(long customerId);
}

