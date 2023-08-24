package com.weble.linkedhouse.house.dto.response;

import com.weble.linkedhouse.customer.dtos.ProfileDto;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.HouseImage;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HouseResponseDto {

    private Long rentalId;
    private ProfileDto host;
    private String description;
    private String location;
    private int price;
    private int maxCapacity;
    private int minCapacity;
    private List<String> imagePath;
    private AutoReservation autoReservation;

    private HouseResponseDto(Long rentalId, ProfileDto host, String description, String location,
                             int price, int maxCapacity, List<String> imagePath, AutoReservation autoReservation) {
        this.rentalId = rentalId;
        this.host = host;
        this.description = description;
        this.location = location;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.imagePath = imagePath;
        this.autoReservation = autoReservation;
    }


    public static HouseResponseDto from(House house) {
        List<String> imagePaths = house.getImagePath().stream()
                .map(HouseImage::getImagePath)
                .toList();

        return new HouseResponseDto(
                house.getRentalId(),
                ProfileDto.from(house.getCustomer().getCustomerProfile()),
                house.getDescription(),
                house.getLocation(),
                house.getPrice(),
                house.getMaxCapacity(),
                imagePaths,
                house.getAutoReservation()
        );
    }
}
