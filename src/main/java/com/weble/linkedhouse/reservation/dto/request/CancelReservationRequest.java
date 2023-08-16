package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.reservation.entity.CancelReservation;

import java.time.LocalDateTime;

public class CancelReservationRequest {


    private House house;

    private Customer customer;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private int reservationNum;

    private CancelReservationRequest(House house, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        this.house = house;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservationRequest from(CancelReservation cancelReservation) {
        return new CancelReservationRequest(
                cancelReservation.getReservation().getHouse(),
                cancelReservation.getCustomer(),
                cancelReservation.getCheckinDate(),
                cancelReservation.getCheckoutDate(),
                cancelReservation.getReservationNum()
        );
    }
}
