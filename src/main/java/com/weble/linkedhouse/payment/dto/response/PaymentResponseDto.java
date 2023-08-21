package com.weble.linkedhouse.payment.dto.response;

import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.payment.entity.Payment;
import com.weble.linkedhouse.reservation.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PaymentResponseDto {

    private House house;
    private Reservation reservation;
    private int price;
    private LocalDateTime requestDay;

    @Builder
    private PaymentResponseDto(House house, Reservation reservation, int price, LocalDateTime requestDay) {
        this.house = house;
        this.reservation = reservation;
        this.price = price;
        this.requestDay = requestDay;
    }

    public static PaymentResponseDto from(Payment payment) {
        return new PaymentResponseDto(
                payment.getHouse(),
                payment.getReservation(),
                payment.getPrice(),
                payment.getRequestDay()
        );
    }
}
