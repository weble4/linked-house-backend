package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AboutJwtException extends LinkedHouseException {


    public AboutJwtException() {
        super(ErrorMessage.JWT_EXCEPTION);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
