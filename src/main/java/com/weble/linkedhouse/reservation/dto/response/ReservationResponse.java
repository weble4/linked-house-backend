package com.weble.linkedhouse.reservation.dto.response;

import com.weble.linkedhouse.reservation.entity.Reservation;
import com.weble.linkedhouse.reservation.entity.constant.PaymentState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {


    private Long reservationId;
    private Long rentalId;
    private Long customerId;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private int reservationNum;
    private PaymentState paymentState;

    public ReservationResponse(Long reservationId, Long rentalId, Long customerId,
                               LocalDateTime checkinDate, LocalDateTime checkoutDate,
                               int reservationNum, PaymentState paymentState) {
        this.reservationId = reservationId;
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
        this.paymentState = paymentState;
    }

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getReservationId(),
                reservation.getHouse().getRentalId(),
                reservation.getCustomer().getCustomerId(),
                reservation.getCheckinDate(),
                reservation.getCheckoutDate(),
                reservation.getReservationNum(),
                reservation.getPaymentState()
        );
    }
}
