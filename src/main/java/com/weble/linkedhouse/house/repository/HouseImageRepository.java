package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.HouseImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseImageRepository extends JpaRepository<HouseImage, Long> {

    void deleteByHouseRentalId(Long rentalId);

    List<HouseImage> findByHouseRentalId(Long rentalId);
}
