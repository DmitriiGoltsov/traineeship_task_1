package com.goltsov.ylab.task.exception;

public class IllegalReadingChange extends RuntimeException {

    public IllegalReadingChange(String message) {
        super(message);
    }

    public IllegalReadingChange(Throwable cause) {
        super(cause);
    }
}
