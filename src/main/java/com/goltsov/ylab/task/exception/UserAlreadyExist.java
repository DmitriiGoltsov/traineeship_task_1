package com.goltsov.ylab.task.exception;

public class UserAlreadyExist extends RuntimeException {

    public UserAlreadyExist(String message) {
        super(message);
    }

    public UserAlreadyExist(Throwable cause) {
        super(cause);
    }
}
