package com.weble.linkedhouse.house.dto.response;

import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HostHouseListResponse {

    private Long rentalId;
    private Long customerId;
    private Integer price;
    private String image;
    private AutoReservation autoReservation;


    public HostHouseListResponse(House house) {
        this.rentalId = house.getRentalId();
        this.customerId = house.getCustomer().getCustomerId();
        this.price = house.getPrice();
        this.image = house.getImage();
        this.autoReservation = house.getAutoReservation();
    }

}
