package com.weble.linkedhouse.customer.entity.constant;

import lombok.Getter;

public enum PublicAt {
    PUBLIC("public"),
    PRIVATE("private");

    @Getter
    private final String reasonPhrase;

    PublicAt(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }
}
