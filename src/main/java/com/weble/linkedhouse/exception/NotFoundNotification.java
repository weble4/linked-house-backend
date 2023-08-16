package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotFoundNotification extends LinkedHouseException{


    public NotFoundNotification() {
        super(ErrorMessage.NOT_FOUND_NOTIFICATION);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
