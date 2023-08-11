package com.weble.linkedhouse.house.dto.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class HouseSaveRequest {
    
    private Customer customer;

    @NotNull(message = "최대 인원을 작성해주세요.")
    private Integer maxCapacity;

    @NotNull(message = "최소 인원을 작성해주세요.")
    private Integer minCapacity;

    @NotNull(message = "가격을 작성해주세요.")
    private Integer price;

    @NotBlank(message = "위치를 작성해주세요.")
    private String location;

    private String image;
    private AutoReservation autoReservation;

    @NotNull(message = "방 갯수를 작성해주세요.")
    private Integer room;

    @NotNull(message = "침대 갯수를 작성해주세요.")
    private Integer bed;

    @NotNull(message = "욕실 갯수를 작성해주세요.")
    private Integer bathRoom;

    private HouseSaveRequest(Customer customer, Integer maxCapacity, Integer minCapacity, Integer price, String location, String image, AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
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

    public House saveHouse() {
        return House.of(customer, maxCapacity, minCapacity, price, location, image,
                autoReservation, room, bed, bathRoom);
    }

    public House toEntity() {
        return House.builder()
                .customer(customer)
                .maxCapacity(maxCapacity)
                .minCapacity(minCapacity)
                .price(price)
                .location(location)
                .image(image)
                .autoReservation(autoReservation)
                .room(room)
                .bed(bed)
                .bathRoom(bathRoom)
                .build();
    }
}
