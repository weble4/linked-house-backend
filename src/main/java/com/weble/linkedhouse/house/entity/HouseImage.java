package com.weble.linkedhouse.house.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HouseImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_image_id")
    private Long houseImageId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalId")
    private House house;

    @Setter
    @Column(name = "image_path")
    private String imagePath;


    private HouseImage(House house, String imagePath) {
        this.house = house;
        this.imagePath = imagePath;
    }

    public static HouseImage of(House house, String imagePath) {
        return new HouseImage(house, imagePath);
    }
}
