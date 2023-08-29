package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class InvalidSignInInformation extends LinkedHouseException{

    public InvalidSignInInformation() {
        super(ErrorMessage.CUSTOMER_NOT_FOUND);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
