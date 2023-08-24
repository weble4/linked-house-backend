package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AlreadyAuthentication extends LinkedHouseException {

    public AlreadyAuthentication() {
        super(ErrorMessage.ALREADY_AUTHORIZATION);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
