package ru.sckibin.messagesender.api.response;

import lombok.Data;

@Data
public class FailedDescription {

    private final String code;
    private final String message;
}
