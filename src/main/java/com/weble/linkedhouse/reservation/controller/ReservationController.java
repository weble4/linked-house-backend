package com.weble.linkedhouse.reservation.controller;

import com.weble.linkedhouse.reservation.dto.response.ReservationResponse;
import com.weble.linkedhouse.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping// 게스트가 자신이 예약한 모든 예약을 조회
    public List<ReservationResponse> findByCustomerId(Long customerId) {
        return reservationService.findByCustomerId(customerId);
    }

    @GetMapping("/details/")
    public ReservationResponse findByReservationId(Long reservationId) {
        return reservationService.findByReservationId(reservationId);
    }

}
