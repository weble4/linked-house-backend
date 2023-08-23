package com.weble.linkedhouse.reservation.dto;

import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDTO {

    // 예약 번호
    private Long reservationId;

    // House 의 rentalId
    private Long rentalId;

    // Reservation 의 customerId
    private Long customerId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;


    public ReservationDTO(Long reservationId, Long rentalId, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.reservationId = reservationId;
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static ReservationDTO of(Long reservationId, Long rentalId, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        return new ReservationDTO(reservationId, rentalId, customerId, checkinDate, checkoutDate, reservationNum);
    }

    public static ReservationDTO from(Reservation entity) {
        return new ReservationDTO(
                entity.getReservationId(),
                entity.getHouse().getRentalId(),
                entity.getCustomer().getCustomerId(),
                entity.getCheckinDate(),
                entity.getCheckoutDate(),
                entity.getReservationNum()
        );
    }
}
