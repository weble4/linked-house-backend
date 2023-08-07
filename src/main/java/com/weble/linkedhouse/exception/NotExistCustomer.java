package com.weble.linkedhouse.exception;

public class NotExistCustomer extends LinkedHouseException {

    public static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NotExistCustomer() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
