package com.weble.linkedhouse.reservation.controller;

import com.weble.linkedhouse.reservation.service.CancelReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class CancelReservationController {

    private final CancelReservationService cancelReservationService;

    @DeleteMapping("/customer/{reservationId}")
    public void deleteReservationByHost(@PathVariable Long reservationId) {
        cancelReservationService.deleteReservationByHost(reservationId);
    }

    @DeleteMapping("/host/{reservationId}")
    public void deleteReservationByCustomer(@PathVariable Long reservationId) {
        cancelReservationService.deleteReservationByCustomer(reservationId);
    }

}
