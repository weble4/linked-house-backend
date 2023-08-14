package com.weble.linkedhouse.house.dto.request;

import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import lombok.Getter;

@Getter
public class UpdateHouseRequestDto {

    private Long RentalId;

    private Integer maxCapacity;

    private Integer minCapacity;

    private Integer price;

    private AutoReservation autoReservation;

    private Integer room;

    private Integer bed;

    private Integer bathRoom;

    private UpdateHouseRequestDto(Integer maxCapacity, Integer minCapacity, Integer price,
                                  AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    public static UpdateHouseRequestDto of(Integer maxCapacity, Integer minCapacity, Integer price,
                                           AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
        return new UpdateHouseRequestDto(maxCapacity, minCapacity, price, autoReservation, room, bed, bathRoom);
    }
}
