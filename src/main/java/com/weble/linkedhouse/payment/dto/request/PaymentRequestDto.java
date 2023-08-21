package com.weble.linkedhouse.payment.dto.request;

import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.payment.entity.Payment;
import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PaymentRequestDto {

    private Long rentalId;
    private Long reservationId;
    private int price;
    private LocalDateTime requestDay;

    @Builder
    private PaymentRequestDto(Long rentalId, Long reservationId, int price, LocalDateTime requestDay) {

        this.rentalId = rentalId;
        this.reservationId = reservationId;
        this.price = price;
        this.requestDay = requestDay;
    }

    public static PaymentRequestDto from(Payment payment) {
        return new PaymentRequestDto(
                payment.getHouse().getRentalId(),
                payment.getReservation().getReservationId(),
                payment.getPrice(),
                payment.getRequestDay()
        );
    }

    public static PaymentRequestDto of(Long rentalId, Long reservationId, int price, LocalDateTime requestDay) {
        return PaymentRequestDto.builder()
                .rentalId(rentalId)
                .reservationId(reservationId)
                .price(price)
                .requestDay(requestDay)
                .build();
    }
}
