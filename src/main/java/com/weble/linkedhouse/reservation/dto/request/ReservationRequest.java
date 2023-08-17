package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.reservation.entity.Reservation;

import java.time.LocalDateTime;

public class ReservationRequest {

    // House 의 rentalId
    private House house;

    // Reservation 의 customerId
    private Customer customer;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;

    private ReservationRequest(House house, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.house = house;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static ReservationRequest of(House house,
                                        Customer customer,
                                        LocalDateTime checkinDate,
                                        LocalDateTime checkoutDate,
                                        Integer reservationNum) {
        return new ReservationRequest(house, customer, checkinDate, checkoutDate, reservationNum);
    }

    public ReservationRequest from(Reservation reservation) {
        return new ReservationRequest(
                reservation.getHouse(),
                reservation.getCustomer(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );
    }

    public Reservation toEntity() {
        return Reservation.builder()
                .house(house)
                .customer(customer)
                .checkinDate(checkinDate)
                .checkoutDate(checkoutDate)
                .reservationNum(reservationNum)
                .build();
    }
}
