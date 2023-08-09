package com.weble.linkedhouse.message.entity.constant;

import lombok.Getter;

public enum ReadCheck {
    READ("read"),
    UNREAD("unread");

    @Getter
    private final String status;

    ReadCheck(String status) {
        this.status = status;
    }
}
