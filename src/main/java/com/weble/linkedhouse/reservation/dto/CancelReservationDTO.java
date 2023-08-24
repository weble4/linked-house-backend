package com.weble.linkedhouse.reservation.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CancelReservationDTO {

    // 취소 번호
    private Long cancelId;

    // 예약 번호
    private Long reservationId;

    // House 의 rentalId
    private Long rentalId;

    // Reservation 의 customerId
    private Long customerId;

    private LocalDateTime checkinDate;

    private LocalDateTime checkoutDate;

    private Integer reservationNum;

    private CancelReservationDTO(Long cancelId, Long reservationId, Long rentalId, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        this.cancelId = cancelId;
        this.reservationId = reservationId;
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservationDTO of(Long cancelId, Long reservationId, Long rentalId, Long customerId, LocalDateTime checkinDate, LocalDateTime checkoutDate, Integer reservationNum) {
        return new CancelReservationDTO(cancelId, reservationId, rentalId, customerId, checkinDate, checkoutDate, reservationNum);
    }
}
