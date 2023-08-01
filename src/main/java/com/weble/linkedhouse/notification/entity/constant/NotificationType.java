package com.weble.linkedhouse.notification.entity.constant;

import lombok.Getter;

public enum NotificationType {

    CHECK("check"),
    ALERT("alert");

    @Getter
    private final String reason;

    NotificationType(String reason){

        this.reason = reason;
    }

}
