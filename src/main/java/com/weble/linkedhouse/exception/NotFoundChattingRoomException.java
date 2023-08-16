package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotFoundChattingRoomException extends LinkedHouseException {


    public NotFoundChattingRoomException() {
        super(ErrorMessage.NOT_FOUND_ROOM);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
