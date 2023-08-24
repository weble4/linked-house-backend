package com.weble.linkedhouse.house.dto.request;

import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateHouseRequestDto {

    private Long rentalId;

    @NotBlank(message = "소개글을 작성해주세요.")
    private String description;

    @NotNull(message = "최대 인원을 작성해주세요.")
    private Integer maxCapacity;

    @NotNull(message = "최소 인원을 작성해주세요.")
    private Integer minCapacity;

    @NotNull(message = "가격을 작성해주세요.")
    private Integer price;

    @NotNull(message = "예약 타입을 확인해주세요")
    private AutoReservation autoReservation;

    @NotNull(message = "방 갯수를 작성해주세요.")
    private Integer room;

    @NotNull(message = "침대 갯수를 작성해주세요.")
    private Integer bed;

    @NotNull(message = "욕실 갯수를 작성해주세요.")
    private Integer bathRoom;

    private UpdateHouseRequestDto(String description, Integer maxCapacity, Integer minCapacity, Integer price,
                                  AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    public static UpdateHouseRequestDto of(String description, Integer maxCapacity, Integer minCapacity, Integer price,
                                           AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
        return new UpdateHouseRequestDto(description,
                maxCapacity,
                minCapacity,
                price,
                autoReservation,
                room,
                bed,
                bathRoom);
    }
}
