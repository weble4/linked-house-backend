package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.reservation.entity.CancelReservation;

import java.time.LocalDateTime;

public class CancelReservationRequest {


    private Long reservationId;

    private Long customerId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private int reservationNum;

    private CancelReservationRequest(Long reservation, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        this.reservationId = reservation;
        this.customerId = customerId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservationRequest from(CancelReservation cancelReservation) {
        return new CancelReservationRequest(
                cancelReservation.getReservation().getReservationId(),
                cancelReservation.getCustomer().getCustomerId(),
                cancelReservation.getCheckinDate(),
                cancelReservation.getCheckoutDate(),
                cancelReservation.getReservationNum()
        );
    }
}
