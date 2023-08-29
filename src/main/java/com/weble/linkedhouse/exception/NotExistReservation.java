package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotExistReservation extends LinkedHouseException{

    public NotExistReservation () {
        super(ErrorMessage.NOT_FOUND_RESERVATION);
    }
    @Override
    public int getStatusCode() {
        return 400;
    }
}
