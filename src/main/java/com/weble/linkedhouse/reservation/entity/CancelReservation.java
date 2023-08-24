package com.weble.linkedhouse.reservation.entity;

import com.weble.linkedhouse.customer.entity.Customer;
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
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CancelReservation {

    @Id
    @Column(name = "cancel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cancelId;

    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "checkin_date", nullable = false)
    private LocalDateTime checkinDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkoutDate;

    @Column(name = "reservation_num", nullable = false)
    private int reservationNum;

    @Builder
    private CancelReservation(Long reservationId, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

    public static CancelReservation of(Long reservationId, Customer customer, LocalDateTime checkinDate, LocalDateTime checkoutDate, int reservationNum) {
        return new CancelReservation(reservationId, customer, checkinDate, checkoutDate, reservationNum);
    }
}
