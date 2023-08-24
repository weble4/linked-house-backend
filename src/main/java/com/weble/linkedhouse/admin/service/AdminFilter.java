package com.weble.linkedhouse.admin.service;

import lombok.Getter;

@Getter
public enum AdminFilter {

    Customer("Customer"),
    Host("Host"),
    SUSPENDED("Suspended");

    private final String description;

    AdminFilter(String description) {
        this.description = description;
    }

}