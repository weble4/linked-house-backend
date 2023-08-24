package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AlreadyExistEmailException extends LinkedHouseException{

    public AlreadyExistEmailException() {
        super(ErrorMessage.DUPLICATED_CUSTOMER);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
