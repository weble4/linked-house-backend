package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>, HouseRepositoryCustom {

    Page<House> findByCustomerCustomerId(Long customerId, Pageable pageable);
}

