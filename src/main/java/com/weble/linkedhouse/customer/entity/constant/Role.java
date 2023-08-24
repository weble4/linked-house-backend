package com.weble.linkedhouse.customer.entity.constant;

import lombok.Getter;

public enum Role {
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_HOST("ROLE_HOST"),
    ROLE_ADMIN("ROLE_ADMIN");

    @Getter
    private final String reason;

    Role(String reason) {
        this.reason = reason;
    }
}
