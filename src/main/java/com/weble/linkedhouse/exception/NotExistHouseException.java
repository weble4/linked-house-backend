package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;


public class NotExistHouseException extends LinkedHouseException {

    public NotExistHouseException() {
        super(ErrorMessage.HOUSE_NOT_FOUND);
    }

    @Override
    public int getStatusCode() { return 400; }
}
