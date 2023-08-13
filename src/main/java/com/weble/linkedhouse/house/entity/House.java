package com.weble.linkedhouse.house.entity;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "house")
public class House extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "customer_id")
    private Customer customer;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "min_capacity")
    private Integer minCapacity;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String location;

    private String image;

    @Column(name = "auto_reservation", nullable = false)
    @Enumerated(EnumType.STRING)
    private AutoReservation autoReservation;

    // 방 갯수
    @Column(nullable = false)
    private Integer room;

    // 침대 갯수
    @Column(nullable = false)
    private Integer bed;

    // 욕실 갯수
    @Column(name = "bath_room", nullable = false)
    private Integer bathRoom;

    @Builder
    private House(Customer customer, int maxCapacity, int minCapacity, int price, String location,
                 String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        this.customer = customer;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.location = location;
        this.image = image;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    public static House of(Customer customer, int maxCapacity, int minCapacity, int price, String location,
                           String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        return new House(customer, maxCapacity, minCapacity, price, location, image, autoReservation, room, bed, bathRoom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House that)) return false;
        return this.getRentalId() != null && getRentalId().equals(that.getRentalId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRentalId());
    }
}
