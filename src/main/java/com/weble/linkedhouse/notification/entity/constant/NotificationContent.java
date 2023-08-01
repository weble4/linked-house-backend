package com.weble.linkedhouse.notification.entity.constant;

import lombok.Getter;

public enum NotificationContent {

    CHECK("check"),
    STOP("stop alert");

    @Getter
    private final String reason;

    NotificationContent(String reason) {
        this.reason = reason;
    }

}