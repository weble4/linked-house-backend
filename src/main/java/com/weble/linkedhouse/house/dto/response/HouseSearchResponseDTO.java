package com.weble.linkedhouse.house.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.weble.linkedhouse.house.entity.House;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseSearchResponseDTO {

    private String location;
    private Integer price;
    private Integer maxCapacity;

    @QueryProjection
    public HouseSearchResponseDTO(String location, Integer price, Integer maxCapacity) {
        this.location = location;
        this.price = price;
        this.maxCapacity = maxCapacity;
    }

    public static HouseSearchResponseDTO from(House house) {
        return new HouseSearchResponseDTO(
                house.getLocation(),
                house.getPrice(),
                house.getMaxCapacity()
        );
    }
}
