package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class Unauthorized extends LinkedHouseException{


    public Unauthorized() {
        super(ErrorMessage.NO_AUTHORIZATION);
    }

    public Unauthorized(Throwable cause) {
        super(ErrorMessage.NO_AUTHORIZATION, cause);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }
}
