package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequest {

    // House 의 rentalId
    private Long rentalId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;

    private ReservationRequest(Long rentalId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.rentalId = rentalId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static ReservationRequest of(Long rentalId,
                                        LocalDateTime checkinDate,
                                        LocalDateTime checkoutDate,
                                        Integer reservationNum) {
        return new ReservationRequest(rentalId, checkinDate, checkoutDate, reservationNum);
    }

    public ReservationRequest from(Reservation reservation) {
        return new ReservationRequest(
                reservation.getHouse().getRentalId(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );
    }
}
