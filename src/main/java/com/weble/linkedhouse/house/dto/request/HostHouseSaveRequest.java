package com.weble.linkedhouse.house.dto.request;

import com.weble.linkedhouse.customer.entity.Customer;
import com.weble.linkedhouse.house.entity.House;
import com.weble.linkedhouse.house.entity.constant.AutoReservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HostHouseSaveRequest {

    @NotNull(message = "최대 인원을 작성해주세요.")
    private Integer maxCapacity;

    @NotNull(message = "최소 인원을 작성해주세요.")
    private Integer minCapacity;

    @NotNull(message = "가격을 작성해주세요.")
    private Integer price;

    @NotBlank(message = "위치를 작성해주세요.")
    private String location;

    @NotBlank(message = "상세 주소를 적어 주세요")
    private String detailAddress;

    @NotNull(message = "예약 타입을 확인해주세요")
    private AutoReservation autoReservation;

    @NotNull(message = "방 갯수를 작성해주세요.")
    private int room;

    @NotNull(message = "침대 갯수를 작성해주세요.")
    private int bed;

    @NotNull(message = "욕실 갯수를 작성해주세요.")
    private int bathRoom;

    private HostHouseSaveRequest(Integer maxCapacity, Integer minCapacity, Integer price, String location,
                                 String detailAddress, AutoReservation autoReservation, Integer room, Integer bed, Integer bathRoom) {
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.price = price;
        this.location = location;
        this.detailAddress = detailAddress;
        this.autoReservation = autoReservation;
        this.room = room;
        this.bed = bed;
        this.bathRoom = bathRoom;
    }

    public House toEntity(Customer customer) {
        return House.builder()
                .customer(customer)
                .maxCapacity(maxCapacity)
                .minCapacity(minCapacity)
                .price(price)
                .location(location)
                .detailAddress(detailAddress)
                .autoReservation(autoReservation)
                .room(room)
                .bed(bed)
                .bathRoom(bathRoom)
                .build();
    }
}
