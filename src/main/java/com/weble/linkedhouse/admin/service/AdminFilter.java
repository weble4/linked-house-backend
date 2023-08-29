package com.weble.linkedhouse.admin.service;

import lombok.Getter;

@Getter
public enum AdminFilter {

    ROLE_HOST("ROLE_HOST"),
    SUSPENDED("Suspended");

    private final String description;

    AdminFilter(String description) {
        this.description = description;
    }

}