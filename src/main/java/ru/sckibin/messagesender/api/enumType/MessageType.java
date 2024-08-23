package ru.sckibin.messagesender.api.enumType;

public enum MessageType {

    EMAIL ("email"),
    UNREALIZED("unrealized");

    public final String value;

    MessageType(String value) {
        this.value = value;
    }
}
