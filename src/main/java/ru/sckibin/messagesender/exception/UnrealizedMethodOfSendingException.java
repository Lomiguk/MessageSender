package ru.sckibin.messagesender.exception;

public class UnrealizedMethodOfSendingException extends RuntimeException {

    public UnrealizedMethodOfSendingException(String message) {
        super(message);
    }
}
