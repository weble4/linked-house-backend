package com.weble.linkedhouse.house.controller;

import com.weble.linkedhouse.house.dto.response.HouseSearchResponseDTO;
import com.weble.linkedhouse.house.service.HouseCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseCustomerController {

    private final HouseCustomerService houseCustomerService;

    @GetMapping
    public List<HouseSearchResponseDTO> findByCondition(@RequestParam String location,
                                                        @RequestParam Integer price,
                                                        @RequestParam Integer maxCapacity) {

        List<HouseSearchResponseDTO> result = houseCustomerService.findAll(location,
                price,
                maxCapacity
        );

        return result;
    }
}
