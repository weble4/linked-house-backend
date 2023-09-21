package com.weble.linkedhouse.reservation.controller;

import com.weble.linkedhouse.reservation.dto.request.ReservationRequest;
import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.service.ReservationService;
import com.weble.linkedhouse.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000" )
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping// 게스트가 자신이 예약한 모든 예약을 조회
    public List<ReservationResponse> findByCustomerCustomerId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reservationService.findByCustomerCustomerId(userDetails.getUserId());
    }

    @GetMapping("/{rentalId}") // 호스트가 자신의 숙소에 접수된 예약 조회
    @PreAuthorize("hasRole('HOST')")
    public List<ReservationResponse> findByHouseRentalId(@PathVariable Long rentalId) {
        return reservationService.findByHouseRentalId(rentalId);
    }

    @PostMapping("/permission/{reservationId}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<String> hostManualPermission(@PathVariable Long reservationId) {
        reservationService.permissionReservation(reservationId);
        return ResponseEntity.ok("예약 승인 하였습니다.");
    }

    @GetMapping("/details/{reservationId}") // 게스트가 개별 예약에 대한 상세조회
    public ReservationResponse findById(@PathVariable Long reservationId) {
        return reservationService.findById(reservationId);
    }


    @PostMapping("/customer/{rentalId}") // 게스트의 예약 신청
    @ResponseStatus(HttpStatus.CREATED)
    public void createReservation(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestBody ReservationRequest request, @PathVariable Long rentalId) {
        reservationService.createReservation(userDetails, request, rentalId);
    }
}
