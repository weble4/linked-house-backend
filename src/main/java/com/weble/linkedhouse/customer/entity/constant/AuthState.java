package com.weble.linkedhouse.customer.entity.constant;

import lombok.Getter;

public enum AuthState {
    AUTH("auth") , NONAUTH("non_auth");

    @Getter
    private final String state;

    AuthState(String state) {
        this.state = state;
    }
}
