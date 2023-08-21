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

    private Long paymentId;
    private House house;
    private Reservation reservation;
    private int price;
    private LocalDateTime requestDay;

    @Builder
    private PaymentRequestDto(Long paymentId, House house, Reservation reservation, int price, LocalDateTime requestDay) {
        this.paymentId = paymentId;
        this.house = house;
        this.reservation = reservation;
        this.price = price;
        this.requestDay = requestDay;
    }

    public static PaymentRequestDto from(Payment payment) {
        return new PaymentRequestDto(
                payment.getPaymentId(),
                payment.getHouse(),
                payment.getReservation(),
                payment.getPrice(),
                payment.getRequestDay()
        );
    }

    public static PaymentRequestDto of(Long paymentId, House house, Reservation reservation, int price, LocalDateTime requestDay) {
        return PaymentRequestDto.builder()
                .paymentId(paymentId)
                .house(house)
                .reservation(reservation)
                .price(price)
                .requestDay(requestDay)
                .build();
    }

    public Payment toEntity() {
        return Payment.of(house, reservation, price, requestDay); // Pass required parameters
    }
}
