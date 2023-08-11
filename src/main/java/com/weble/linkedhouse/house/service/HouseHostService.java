package com.weble.linkedhouse.house.service;

import com.weble.linkedhouse.customer.entity.Customer;
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
        House house = houseRepository.findByRentalId(rentalId).orElseThrow(NotExistHouseException::new);
        HostHouseResponse response = HostHouseResponse.of(house);
        return response;
    }

    @Transactional
    public House save(HostHouseSaveRequest request) {
        Customer customer = request.getCustomer();

        House house =  House.of(
                customer,
                request.getMaxCapacity(),
                request.getMinCapacity(),
                request.getPrice(),
                request.getLocation(),
                request.getImage(),
                request.getAutoReservation(),
                request.getRoom(),
                request.getBed(),
                request.getBathRoom()
        );
        return houseRepository.save(house);
    }

    @Transactional
    public void delete(Long rentalId) {

        House house = houseRepository.findByRentalId(rentalId).orElseThrow(NotExistHouseException::new);

        houseRepository.delete(house);
    }

}
