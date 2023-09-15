package com.weble.linkedhouse.house.controller;

import com.weble.linkedhouse.house.dto.request.HostHouseSaveRequest;
import com.weble.linkedhouse.house.dto.request.UpdateHouseRequestDto;
import com.weble.linkedhouse.house.dto.response.HouseResponseDto;
import com.weble.linkedhouse.house.service.HouseHostService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/host/houses")
public class HouseHostController {

    private final HouseHostService houseHostService;

    @GetMapping
    public ResponseEntity<Page<HouseResponseDto>> findMyRegistrationHouseList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC) Pageable pageable) {

        Page<HouseResponseDto> result = houseHostService.findMyRegistrationHouseList(userDetails, pageable);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{rentalId}")
    public ResponseEntity<HouseResponseDto> findMyRegistrationHouse(@PathVariable Long rentalId) {
        HouseResponseDto result = houseHostService.findMyRegistrationHouse(rentalId);

        return  ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<String> registrationHouse(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart @Valid HostHouseSaveRequest request,
            @RequestPart List<MultipartFile> images
            ) {

        houseHostService.registrationHouse(userDetails, request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body("생성에 성공하였습니다.");
    }

    @PatchMapping("/{rentalId}")
    public ResponseEntity<HouseResponseDto> updateHouse(@RequestPart List<MultipartFile> images,
                                                        @RequestPart @Valid UpdateHouseRequestDto update
                                                        ) {
        HouseResponseDto result = houseHostService.updateHouse(images, update);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/reservation/{rentalId}")
    public ResponseEntity<String> updateReservationSetting(@PathVariable Long rentalId,
                                                           @RequestBody UpdateHouseRequestDto update) {
        houseHostService.updateReservation(update, rentalId);
        return ResponseEntity.ok("update complete");
    }

    @DeleteMapping("/{rentalId}")
    public void delete(@PathVariable Long rentalId) {
        houseHostService.delete(rentalId);
    }

}
