package com.weble.linkedhouse.house.dto;

import lombok.Getter;

public enum FilterKeyword {
    SEOUL("서울"),
    SEJONG("세종"),
    INCHEON("인천"),
    DAEJEON("대전"),
    GWANGJU("광주"),
    BUSAN("부산"),
    DAEGU("대구"),
    ULSAN("울산"),
    GYENGGIDO("경기도"),
    GANGWONDO("강원도"),
    CHUNGCHEONGDO("충청도"),
    JEOLLADO("전라도"),
    GYEONGSANGDO("경상도"),
    JEJU("제주도");

    @Getter
    private final String description;

    FilterKeyword(String description) {
        this.description = description;
    }
}
