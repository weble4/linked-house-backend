package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotExistPayment extends LinkedHouseException {

    public NotExistPayment() {
        super(ErrorMessage.NOT_FOUND_PAYMENT);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}