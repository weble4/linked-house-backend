package com.weble.linkedhouse.exception;

public class AlreadyExistEmailException extends LinkedHouseException{

    public static final String MESSAGE = "이미 가입된 이메일입니다";

    public AlreadyExistEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
