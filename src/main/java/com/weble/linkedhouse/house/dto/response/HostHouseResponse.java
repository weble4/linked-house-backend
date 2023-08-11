package com.weble.linkedhouse.house.dto.response;

import com.weble.linkedhouse.customer.dtos.response.LoginResponse;
import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.customer.entity.CustomerProfile;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import com.weble.linkedhouse.security.jwt.token.TokenDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HostHouseResponse {

    private Long rentalId;
    private Long customerId;
    private Integer price;
    private String image;
    private AutoReservation autoReservation;

    @Builder
    public HostHouseResponse(Long rentalId, Long customerId, Integer price, String image, AutoReservation autoReservation) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.price = price;
        this.image = image;
        this.autoReservation = autoReservation;
    }

    public static HostHouseResponse of(House house) {
        return HostHouseResponse.builder()
                .rentalId(house.getRentalId())
                .customerId(house.getCustomer().getCustomerId())
                .price(house.getPrice())
                .image(house.getImage())
                .autoReservation(house.getAutoReservation()).build();
    }

}
