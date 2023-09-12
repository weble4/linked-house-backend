package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseCustomerService {

    private final HouseRepository houseRepository;

    public Page<HouseResponseDto> findAllHouse(FilterKeyword filterKeyword, SearchKeyword searchKeyword, Pageable pageable) {
       return houseRepository.findAllHouse(filterKeyword, searchKeyword, pageable).map(HouseResponseDto::from);
    }

    public HouseResponseDto findHouse(Long rentalId) {
        return houseRepository.findByIdWithCustomer(rentalId).map(HouseResponseDto::from)
                .orElseThrow(NotExistHouseException::new);
    }
}
