package com.weble.linkedhouse.house.entity.constant;

import lombok.Getter;

public enum AutoReservation {
    AUTO("auto"), MANUAL("manual");

    @Getter
    private final String autoReservation;

    AutoReservation(String autoReservation) {
        this.autoReservation = autoReservation;
    }

}
