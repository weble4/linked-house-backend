package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseCustomerService {

    private final HouseRepository houseRepository;
    private final CustomerRepository customerRepository;

    public List<House> findAll(String location, Integer price, Integer maxCapacity) {
        Specification<House> specification = Specification.where(null);

        if(location != null) {
            specification = specification.and(HouseSpecification.equalLocation(location));
        }

        if(price != null) {
            specification = specification.and(HouseSpecification.equalPrice(price));
        }

        if(maxCapacity != null) {
            specification = specification.and(HouseSpecification.equalMaxCapacity(maxCapacity));
        }

        return houseRepository.findAll(specification);
    }
}
