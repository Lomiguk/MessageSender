package ru.sckibin.messagesender.api.response;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageResponse {

    private UUID messageId;
    private String recipient;
    private String status;
}
