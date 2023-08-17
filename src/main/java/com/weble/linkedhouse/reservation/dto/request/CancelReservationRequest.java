package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.reservation.entity.CancelReservation;
import com.weble.linkedhouse.reservation.entity.Reservation;

import java.time.LocalDateTime;

public class CancelReservationRequest {


    private Reservation reservation;

    private Customer customer;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private int reservationNum;

    private CancelReservationRequest(Reservation reservation, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        this.reservation = reservation;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservationRequest from(CancelReservation cancelReservation) {
        return new CancelReservationRequest(
                cancelReservation.getReservation(),
                cancelReservation.getCustomer(),
                cancelReservation.getCheckinDate(),
                cancelReservation.getCheckoutDate(),
                cancelReservation.getReservationNum()
        );
    }

    public CancelReservation toEntity() {
        return CancelReservation.builder()
                .reservation(reservation)
                .customer(customer)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .reservationNum(reservationNum)
                .build();
    }
}
