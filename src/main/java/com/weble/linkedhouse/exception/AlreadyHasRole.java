package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AlreadyHasRole extends LinkedHouseException {

    public AlreadyHasRole() {
        super(ErrorMessage.ALREADY_HAS_ROLE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
