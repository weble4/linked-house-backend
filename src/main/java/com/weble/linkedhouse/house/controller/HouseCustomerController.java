package com.weble.linkedhouse.house.controller;

import com.weble.linkedhouse.house.dto.FilterKeyword;
import com.weble.linkedhouse.house.dto.SearchKeyword;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.service.HouseCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/houses")
public class HouseCustomerController {

    private final HouseCustomerService houseCustomerService;

    @GetMapping
    public Page<HouseResponseDto> findAllHouse(
            @RequestBody(required = false) FilterKeyword filterKeyword,
            @RequestBody(required = false) SearchKeyword searchKeyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return houseCustomerService.findAllHouse(filterKeyword, searchKeyword, pageable);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<HouseResponseDto> getDetailHouseInfo(@PathVariable Long rentalId) {
        HouseResponseDto result = houseCustomerService.findHouse(rentalId);
        return ResponseEntity.ok().body(result);
    }
}
