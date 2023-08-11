package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByCustomerId(Long customerId);

    Optional<House> findByRentalId(Long rentalId);

}

