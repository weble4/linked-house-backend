package com.weble.linkedhouse.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long reservationId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalId")
    private House house;

    @ManyToOne (optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Column(name="checkin_date", nullable = false)
    private Localdatetime checkinDate;

    @Column(name="checkout_date", nullable = false)
    private Localdatetime checkoutDate;

    @Column(name="reservation_num", nullable = false)
    private int reservationNum;

    @Builder
    private Reservation(House house, Customer customer, localdatetime checkinDate,
                        localdatetime checkoutDate, int reservationNum) {
            this.House = house;
            this.Customer = customer;
            this.checkinDate = checkinDate;
            this.checkoutDate = checkoutDate;
            this.reservationNum = reservationNum;
    }
}
