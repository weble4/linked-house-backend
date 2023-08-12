package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House>, HouseRepositoryCustom  {

    List<House> findByCustomerId(Long customerId);

    Optional<House> findByRentalId(Long rentalId);

}

