package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.house.dto.response.HouseSearchResponseDTO;
import com.weble.linkedhouse.house.repository.HouseRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseCustomerService {

    private final HouseRepositoryCustomImpl houseRepositoryCustom;

    public List<HouseSearchResponseDTO> findAll(String location, Integer price, Integer maxCapacity) {

        return houseRepositoryCustom.findByCondition(location, price, maxCapacity);
    }
}
