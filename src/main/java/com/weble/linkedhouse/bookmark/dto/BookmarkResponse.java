package com.weble.linkedhouse.bookmark.dto;

import lombok.Getter;

@Getter
public class BookmarkResponse {

    private Long rentalId;
    private int price;
    private String location;
    private String imagePath;

    private BookmarkResponse(Long rentalId, int price, String location, String imagePath) {
        this.rentalId = rentalId;
        this.price = price;
        this.location = location;
        this.imagePath = imagePath;
    }

    public static BookmarkResponse of(Long rentalId, int price, String location, String imagePath) {
        return new BookmarkResponse(rentalId, price, location, imagePath);
    }
}
