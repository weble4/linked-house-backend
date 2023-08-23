package com.weble.linkedhouse.payment.entity;

import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.reservation.entity.Reservation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalId")
    private House house;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    private int price;

    @Column(name = "request_day")
    private LocalDateTime requestDay;

    @Builder
    private Payment(House house, Reservation reservation, int price) {
        this.house = house;
        this.reservation = reservation;
        this.price = price;
        this.requestDay = LocalDateTime.now();
    }

    public static Payment of(House house, Reservation reservation, int price) {
        return new Payment(house, reservation, price);
    }
}
