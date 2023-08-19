package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;

public class NotReportReview extends LinkedHouseException {

    public NotReportReview() {
        super(ErrorMessage.CAN_NOT_REPORT);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
