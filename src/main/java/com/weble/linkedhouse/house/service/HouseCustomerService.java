package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.LinkedHouseApplication;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HouseCustomerService {

    private final HouseRepository houseRepository;

    public Page<HouseResponseDto> findAllHouse(String location, Integer minPrice, Integer maxPrice, Integer room, Integer bed, Pageable pageable) {
        Logger logger = LoggerFactory.getLogger(LinkedHouseApplication.class);
        logger.info("API 로깅");
        return houseRepository.findAllHouse(location, minPrice, maxPrice, room, bed, pageable).map(HouseResponseDto::from);
    }

    public HouseResponseDto findHouse(Long rentalId) {
        return houseRepository.findByIdWithCustomer(rentalId).map(HouseResponseDto::from)
                .orElseThrow(NotExistHouseException::new);
    }
}
