package com.weble.linkedhouse.reservation.entity;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.reservation.entity.constant.PaymentState;
import com.weble.linkedhouse.reservation.entity.constant.ReservationState;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalId")
    private House house;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @Column(name = "checkin_date", nullable = false)
    private LocalDateTime checkinDate;

    @Column(name = "checkout_date", nullable = false)
    private LocalDateTime checkoutDate;

    @Column(name = "reservation_num", nullable = false)
    private int reservationNum;

    @Column(name = "payment_state", nullable = false,
            columnDefinition = "varchar(50) default 'NOT_PAY'")
    private PaymentState paymentState;

    @Enumerated(EnumType.STRING)
    private ReservationState reservationState;

    @Builder
    private Reservation(House house, Customer customer, LocalDateTime checkinDate,
                        LocalDateTime checkoutDate, int reservationNum, ReservationState reservationState) {
        this.house = house;
        this.customer = customer;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
        this.paymentState = PaymentState.NOT_PAY;
        this.reservationState = reservationState;
    }

    public static Reservation of(House house, Customer customer, LocalDateTime checkinDate,
                          LocalDateTime checkoutDate, int reservationNum, ReservationState reservationState) {
        return new Reservation(house, customer, checkinDate, checkoutDate, reservationNum, reservationState);
    }

    public void permission() {
        this.reservationState = ReservationState.PERMISSION;
    }

    public void payComplete() {
        this.paymentState = PaymentState.PAY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return this.getReservationId() != null && getReservationId().equals(that.getReservationId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReservationId());
    }
}
