package com.weble.linkedhouse.exception;

import com.weble.linkedhouse.exception.response.ErrorMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class LinkedHouseException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public LinkedHouseException(ErrorMessage message) {
        super(message.getMessage());
    }

    public LinkedHouseException(ErrorMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
