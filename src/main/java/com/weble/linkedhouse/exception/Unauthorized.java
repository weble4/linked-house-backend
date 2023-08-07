package com.weble.linkedhouse.exception;

public class Unauthorized extends LinkedHouseException{

    private static final String MESSAGE = "인증이 필요합니다";

    public Unauthorized() {
        super(MESSAGE);
    }

    public Unauthorized(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 403;
    }
}
