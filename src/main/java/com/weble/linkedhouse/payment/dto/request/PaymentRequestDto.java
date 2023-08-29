package com.weble.linkedhouse.payment.dto.request;

import com.weble.linkedhouse.payment.entity.Payment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentRequestDto {

    private Long rentalId;
    private Long reservationId;
    private int price;

    @Builder
    private PaymentRequestDto(Long rentalId, Long reservationId, int price) {
        this.rentalId = rentalId;
        this.reservationId = reservationId;
        this.price = price;
    }

    public static PaymentRequestDto from(Payment payment) {
        return new PaymentRequestDto(
                payment.getHouse().getRentalId(),
                payment.getReservation().getReservationId(),
                payment.getPrice()
        );
    }

    public static PaymentRequestDto of(Long rentalId, Long reservationId, int price) {
        return PaymentRequestDto.builder()
                .rentalId(rentalId)
                .reservationId(reservationId)
                .price(price)
                .build();
    }
}
