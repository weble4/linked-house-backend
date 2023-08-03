package com.weble.linkedhouse.house.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter @Getter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
public class HouseSearchDTO {
    private String location;
    private int price;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private int reservationNum;

    public HouseSearchDTO(String location, int price, LocalDateTime checkinDate, LocalDateTime checkoutDate,
                          int reservationNum) {
        this.location = location;
        this.price = price;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.reservationNum = reservationNum;
    }

}
