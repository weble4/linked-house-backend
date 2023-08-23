package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AlreadyPayReservationException extends LinkedHouseException{

    public AlreadyPayReservationException() {
        super(ErrorMessage.ALREADY_PAY_RESERVATION);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
