package com.weble.linkedhouse.house.controller;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.service.CustomerService;
import com.weble.linkedhouse.exception.NotExistHouseException;
import com.weble.linkedhouse.house.dto.request.HostHouseSaveRequest;
import com.weble.linkedhouse.house.dto.response.HostHouseListResponse;
import com.weble.linkedhouse.house.dto.response.HostHouseResponse;
import com.weble.linkedhouse.house.dto.response.HostHouseSaveResponse;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.service.HouseHostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/host/houses")
public class HouseHostController {

    private final HouseHostService houseHostService;

    @GetMapping("")
    public ResponseEntity<List<HostHouseListResponse>> findByCustomerId(@RequestParam Long customerId) {

        List<HostHouseListResponse> responses = houseHostService.findByCustomerId(customerId);

        return ResponseEntity.ok().body(responses);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<HostHouseResponse> findByRentalId(@PathVariable Long rentalId) {
        HostHouseResponse response = houseHostService.findByRentalId(rentalId);

        return  ResponseEntity.ok().body(response);
    }

    @PostMapping("")
    public HostHouseSaveResponse save(HostHouseSaveRequest request) {
        return houseHostService.save(request);
    }

    @DeleteMapping("")
    public void delete(Long rentalId) {
        houseHostService.delete(rentalId);
    }

}
