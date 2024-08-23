package ru.sckibin.messagesender.exception;

public class ExpectedEntityNotFoundException extends RuntimeException {

    public ExpectedEntityNotFoundException() {
        super();
    }

    public ExpectedEntityNotFoundException(String message) {
        super(message);
    }
}
