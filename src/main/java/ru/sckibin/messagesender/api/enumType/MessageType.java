package ru.sckibin.messagesender.api.enumType;

public enum MessageType {

    EMAIL ("email");

    public final String value;

    MessageType(String value) {
        this.value = value;
    }
}
