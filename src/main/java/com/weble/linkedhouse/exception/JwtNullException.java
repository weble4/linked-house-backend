package com.weble.linkedhouse.exception;

public class JwtNullException extends LinkedHouseException {

    public static final String MESSAGE = "JWT Token can't be Null or Empty";

    public JwtNullException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
