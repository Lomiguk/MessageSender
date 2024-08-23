package ru.sckibin.messagesender.api.response;

import lombok.Data;

import java.util.UUID;

@Data
public class StatusResponse {

    private UUID messageId;
    private String status;
    //TODO history of status changing
}
