package ru.sckibin.messagesender.api.enumType;

public enum MessageFailedCode {
    UNEXPECTED_EXCEPTION ("1", "Unexpected exception"),
    UNREALIZED_TYPE_EXCEPTION ("2", "Sending messages of this type is not implemented"),
    MAIL_EXCEPTION ("3", "Mail exception"),
    DB_EXCEPTION ("4", "Database exception"),
    VALIDATION_EXCEPTION ("5", "Message validation exception");

    public final String code;
    public final String header;

    MessageFailedCode(String code, String header) {
        this.code = code;
        this.header = header;
    }
}
