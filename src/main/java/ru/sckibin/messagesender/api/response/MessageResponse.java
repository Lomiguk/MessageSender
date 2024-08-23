package ru.sckibin.messagesender.api.response;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageResponse {

    private UUID messageId;
    private String type;
    private String status;
    private String recipient;
    private String failedDescription = null;
}
