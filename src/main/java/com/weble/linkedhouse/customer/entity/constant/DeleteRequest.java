package com.weble.linkedhouse.customer.entity.constant;

import lombok.Getter;

public enum DeleteRequest {
    DELETE("yes"),
    NOT_DELETE("no");

    @Getter
    private final String reason;

    DeleteRequest(String reason) {
        this.reason = reason;
    }
}
