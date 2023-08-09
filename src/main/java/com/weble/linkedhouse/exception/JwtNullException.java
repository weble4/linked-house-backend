package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class JwtNullException extends LinkedHouseException {


    public JwtNullException() {
        super(ErrorMessage.JWT_NOT_NULL);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
