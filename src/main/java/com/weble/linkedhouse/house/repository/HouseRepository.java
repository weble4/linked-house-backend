package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House>, HouseRepositoryCustom  {

    List<House> findByCustomerId(Long customerId);

    Optional<House> findByRentalId(Long rentalId);

}

