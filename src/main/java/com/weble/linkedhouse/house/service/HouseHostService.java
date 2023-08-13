package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.customer.repository.CustomerRepository;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.dto.request.HostHouseSaveRequest;
import com.weble.linkedhouse.house.dto.response.HostHouseListResponse;
import com.weble.linkedhouse.house.dto.response.HostHouseResponse;
import com.weble.linkedhouse.house.dto.response.HostHouseSaveResponse;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HouseHostService {

    private final HouseRepository houseRepository;
    private final CustomerRepository customerRepository;

    public List<HostHouseListResponse> findByCustomerId(Long customerId) {

        if(customerRepository.findById(customerId).isEmpty()){
            throw new NotExistHouseException();
        }

        List<House> houses = houseRepository.findByCustomerId(customerId);

        return houses
            .stream()
            .map(HostHouseListResponse::new)
            .collect(Collectors.toList());
    }

    public HostHouseResponse findByRentalId(Long rentalId) {
        HostHouseResponse response = HostHouseResponse.of(
                houseRepository.findByRentalId(rentalId).orElseThrow(NotExistHouseException::new)
        );
        return response;
    }

    @Transactional
    public HostHouseSaveResponse save(HostHouseSaveRequest request) {

        return houseRepository.save(request);
    }

    @Transactional
    public void delete(Long rentalId) {

        House house = houseRepository.findByRentalId(rentalId).orElseThrow(NotExistHouseException::new);

        houseRepository.delete(house);
    }

}
