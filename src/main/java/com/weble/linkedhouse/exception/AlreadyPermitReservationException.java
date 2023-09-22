package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class AlreadyPermitReservationException extends LinkedHouseException {

    public AlreadyPermitReservationException() {
        super(ErrorMessage.ALREADY_PERMIT_RESERVATION);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
