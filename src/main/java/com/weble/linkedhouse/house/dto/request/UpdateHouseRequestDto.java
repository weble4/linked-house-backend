package com.weble.linkedhouse.house.dto.request;

import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateHouseRequestDto {

    private Long RentalId;

    @NotBlank(message = "최대 인원을 작성해주세요.")
    private Integer maxCapacity;

    @NotBlank(message = "최소 인원을 작성해주세요.")
    private Integer minCapacity;

    @NotBlank(message = "가격을 작성해주세요.")
    private Integer price;

    @NotBlank(message = "예약 타입을 확인해주세요")
    private AutoReservation autoReservation;

    @NotBlank(message = "방 갯수를 작성해주세요.")
    private Integer room;

    @NotBlank(message = "침대 갯수를 작성해주세요.")
    private Integer bed;

    @NotBlank(message = "욕실 갯수를 작성해주세요.")
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
