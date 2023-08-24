package com.weble.linkedhouse.payment.dto.response;

import com.weble.linkedhouse.payment.entity.Payment;
import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentResponseDto {

    private Long paymentId;
    private Long rentalId;
    private Long reservationId;
    private int price;
    private LocalDateTime requestDay;

    private PaymentResponseDto(Long paymentId, Long rentalId, Long reservationId, int price, LocalDateTime requestDay) {
        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.reservationId = reservationId;
        this.price = price;
        this.requestDay = requestDay;
    }

    public static PaymentResponseDto from(Payment payment) {
        return new PaymentResponseDto(
                payment.getPaymentId(),
                payment.getHouse().getRentalId(),
                payment.getReservation().getReservationId(),
                payment.getPrice(),
                payment.getRequestDay()
        );
    }
}
