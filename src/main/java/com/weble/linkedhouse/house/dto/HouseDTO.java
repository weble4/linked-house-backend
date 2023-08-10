package com.weble.linkedhouse.house.dto;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDTO {
    private Customer customer;

    private int maxCapacity;

    private int minCapacity;

    private int price;

    private String location;

    private String image;

    private AutoReservation autoReservation;

    private int room;

    private int bed;

    private int bathRoom;

    public HouseDTO(Customer customer, int maxCapacity, int minCapacity, int price, String location, String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
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


    public static HouseDTO of(Customer customer, int maxCapacity, int minCapacity, int price, String location, String image, AutoReservation autoReservation, int room, int bed, int bathRoom) {
        return new HouseDTO(customer, maxCapacity, minCapacity, price, location, image, autoReservation, room, bed, bathRoom);
    }

    public static HouseDTO from(House entity) {
        return new HouseDTO(
                entity.getCustomer(),
                entity.getMaxCapacity(),
                entity.getMinCapacity(),
                entity.getPrice(),
                entity.getLocation(),
                entity.getImage(),
                entity.getAutoReservation(),
                entity.getRoom(),
                entity.getBed(),
                entity.getBathRoom()
        );
    }

    public House toEntity() {
        return House.of(customer, maxCapacity, minCapacity, price, location, image, autoReservation, room, bed, bathRoom);
    }
}
