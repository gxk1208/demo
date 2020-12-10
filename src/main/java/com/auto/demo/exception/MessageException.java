package com.auto.demo.exception;

public class MessageException extends RuntimeException {
    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }
}
