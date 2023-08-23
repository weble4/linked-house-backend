package com.weble.linkedhouse.reservation.dto.request;

import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequest {

    // House 의 rentalId
    private Long rentalId;

    // Reservation 의 customerId
    private Long customerId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;

    private ReservationRequest(Long rentalId, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static ReservationRequest of(Long rentalId,
                                        Long customerId,
                                        LocalDateTime checkinDate,
                                        LocalDateTime checkoutDate,
                                        Integer reservationNum) {
        return new ReservationRequest(rentalId, customerId, checkinDate, checkoutDate, reservationNum);
    }

    public ReservationRequest from(Reservation reservation) {
        return new ReservationRequest(
                reservation.getHouse().getRentalId(),
                reservation.getCustomer().getCustomerId(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum()
        );
    }
}
