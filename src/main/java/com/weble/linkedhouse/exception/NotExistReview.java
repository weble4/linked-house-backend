package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotExistReview extends LinkedHouseException{

    public NotExistReview () {
        super(ErrorMessage.NOT_FOUND_REVIEW);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
