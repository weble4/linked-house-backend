package com.weble.linkedhouse.reservation.dto;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDTO {

    // 예약 번호
    private Long reservationId;

    // House 의 rentalId
    private House house;

    // Reservation 의 customerId
    private Customer customer;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;


    public ReservationDTO(Long reservationId, House house, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.reservationId = reservationId;
        this.house = house;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static ReservationDTO of(Long reservationId, House house, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        return new ReservationDTO(reservationId, house, customer, checkinDate, checkoutDate, reservationNum);
    }

    public static ReservationDTO from(Reservation entity) {
        return new ReservationDTO(
                entity.getReservationId(),
                entity.getHouse(),
                entity.getCustomer(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getReservationNum()
        );
    }

    public ReservationDTO toEntity() {
        return ReservationDTO.of(reservationId, house, customer, checkinDate, checkoutDate, reservationNum);
    }
}
