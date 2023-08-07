package com.weble.linkedhouse.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CancelReservation {

    @Id
    @Column(name = "cancel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    @Column(name = "checkin_date", nullable = false)
    private LocalDateTime checkinDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkoutDate;

    @Column(name = "reservation_num", nullable = false)
    private int reservationNum;


    private CancelReservation(Reservation reservation, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        this.reservation = reservation;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservation of(Reservation reservation, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        return new CancelReservation(reservation, checkinDate, checkoutDate, reservationNum);
    }
}
