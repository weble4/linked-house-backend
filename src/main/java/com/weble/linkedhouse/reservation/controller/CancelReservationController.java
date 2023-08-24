package com.weble.linkedhouse.reservation.controller;

import com.weble.linkedhouse.reservation.service.CancelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class CancelReservationController {

    private final CancelReservationService cancelReservationService;

    @DeleteMapping("/host/{reservationId}")
    public void deleteReservationByHost(@PathVariable Long reservationId) {
        cancelReservationService.deleteReservationByHost(reservationId);
    }

    @DeleteMapping("/customer/{reservationId}")
    public void deleteReservationByCustomer(@PathVariable Long reservationId) {
        cancelReservationService.deleteReservationByCustomer(reservationId);
    }
}
