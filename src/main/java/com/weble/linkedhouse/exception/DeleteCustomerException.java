package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class DeleteCustomerException extends LinkedHouseException {

    public DeleteCustomerException() {
        super(ErrorMessage.DELETE_CUSTOMER);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
