package com.weble.linkedhouse.customer.entity.constant;

import lombok.Getter;

public enum Role {
    CUSTOMER("ROLE_CUSTOMER"),
    HOST("ROLE_HOST"),
    ADMIN("ROLE_ADMIN");

    @Getter
    private final String reason;

    Role(String reason) {
        this.reason = reason;
    }
}
