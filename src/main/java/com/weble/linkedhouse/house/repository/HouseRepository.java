package com.weble.linkedhouse.house.repository;

import com.weble.linkedhouse.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface HouseRepository extends JpaRepository<House, Long>, HouseCustomRepository {

    // HouseCustomRepository 를 상속했으므로 내부의 findAll 사용 가능
    // 아래는 호스트가 이용할 기능

    List<House> findByHost(long host);

    Optional<House> findByRentalId(long rentalId);

    List<House> findByTitle(String title);

    @Transactional
    void deleteByRentalId(long rentalId);

}
