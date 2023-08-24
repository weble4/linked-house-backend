package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotExistCustomer extends LinkedHouseException {

    public NotExistCustomer() {
        super(ErrorMessage.NOT_FOUND_CUSTOMER);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
