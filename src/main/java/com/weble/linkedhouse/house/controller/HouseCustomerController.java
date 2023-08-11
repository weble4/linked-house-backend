package com.weble.linkedhouse.house.controller;

import com.weble.linkedhouse.house.dto.HouseDTO;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.service.HouseCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseCustomerController {

    private final HouseCustomerService houseCustomerService;

    @GetMapping("")
    public List<HouseDTO> findAll(@RequestParam(required = false) String location,
                                  @RequestParam(required = false) Integer price,
                                  @RequestParam(required = false) Integer maxCapacity) {
        List<House> houses = houseCustomerService.findAll(location, price, maxCapacity);

        List<HouseDTO> houseDTOS = new ArrayList<>();

        for(House house : houses) {
            houseDTOS.add(HouseDTO.from(house));
        }

        return houseDTOS;
    }
}
