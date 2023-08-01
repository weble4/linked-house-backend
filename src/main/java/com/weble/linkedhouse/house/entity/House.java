package com.weble.linkedhouse.house.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rental_id", nullable = false)
    private long rentalId;

    // @ManyToOne(targetEntity = Host.class, fetch=FetchType.LAZY)
    // @JoinColumn(name = "host_id")
    // Host Entity 에서 정의 될 ID 키에 대한 내용, 추후 Host 작성 완료 시 주석 해제
    private long hostId;

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
    @Enumerated(EnumType.STRING) // constant 의 ENUM AutoReservation 과 연결
    private String autoReservation;

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
    public House(long rentalId, long hostId, int maxCapacity, int minCapacity, int price, String location,
                 String image, String autoReservation, int room, int bed, int bathRoom) {
        this.rentalId = rentalId;
        this.hostId = hostId;
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

}
