package com.weble.linkedhouse.house.entity;


import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.dto.request.UpdateHouseRequestDto;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.util.AuditingFields;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "house",
        indexes = {
                @Index(columnList = "createdAt"),
        }
)
public class House extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Long rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String description;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "min_capacity")
    private int minCapacity;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String detailAddress;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "house")
    private List<HouseImage> imagePath = new ArrayList<>();

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

    //연관관계 편의 메서드
    public void addImagePath(HouseImage imagePath) {
        this.imagePath.add(imagePath);
        imagePath.setHouse(this);
    }

    public void updateHouse(String description, int maxCapacity,int minCapacity, int price,
                            AutoReservation autoReservation, int room, int bed, int bathRoom) {
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    @Builder
    private House(Customer customer, String description, int maxCapacity, int minCapacity, int price, String location,
                  String detailAddress, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        this.customer = customer;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.location = location;
        this.detailAddress = detailAddress;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    public static House of(Customer customer, String description, int maxCapacity, int minCapacity, int price,
                           String location, String detailAddress, AutoReservation autoReservation,
                           int room, int bed, int bathRoom) {
        return new House(
                customer,
                description,
                maxCapacity,
                minCapacity,
                price,
                location,
                detailAddress,
                autoReservation,
                room,
                bed,
                bathRoom);
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
