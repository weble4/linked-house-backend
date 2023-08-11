package com.weble.linkedhouse.house.dto.response;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HostHouseSaveResponse {

    private Customer customer;
    private Integer maxCapacity;
    private Integer minCapacity;
    private Integer price;
    private String location;
    private String image;
    private AutoReservation autoReservation;
    private Integer room;
    private Integer bed;
    private Integer bathRoom;

    public HostHouseSaveResponse(House house) {
        this.customer = house.getCustomer();
        this.maxCapacity = house.getMaxCapacity();
        this.minCapacity = house.getMinCapacity();
        this.price = house.getPrice();
        this.location = house.getLocation();
        this.image = house.getImage();
        this.autoReservation = house.getAutoReservation();
        this.room = house.getRoom();
        this.bed = house.getBed();
        this.bathRoom = house.getBathRoom();
    }


}
