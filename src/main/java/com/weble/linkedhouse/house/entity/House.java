package com.weble.linkedhouse.house.entity;


import com.weble.linkedhouse.host.entity.Host;
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
public class House extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private Host host;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "min_capacity")
    private int minCapacity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    private String image;

    @Column(name = "auto_reservation", nullable = false)
    @Enumerated(EnumType.STRING)
    private AutoReservation autoReservation;

    // 방 갯수
    @Column(nullable = false)
    private int room;

    // 침대 갯수
    @Column(nullable = false)
    private int bed;

    // 욕실 갯수
    @Column(name = "bath_room", nullable = false)
    private int bathRoom;

    @Builder
    private House(Host host, int maxCapacity, int minCapacity, int price, String location,
                 String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        addHost(host);
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

    public static House of(Host host, int maxCapacity, int minCapacity, int price, String location,
                           String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        return new House(host, maxCapacity, minCapacity, price, location, image, autoReservation, room, bed, bathRoom);
    }

    public void addHost(Host host) {
        this.host = host;
        host.getHouses().add(this);
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
