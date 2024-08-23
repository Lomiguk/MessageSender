package ru.sckibin.messagesender.api.dto;

import lombok.Builder;
import lombok.Data;
import ru.sckibin.messagesender.api.enumType.MessageStatus;
import ru.sckibin.messagesender.api.enumType.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class MessageDTO {

    private UUID id;
    private String recipient;
    private MessageType type;
    private MessageStatus status;
    private String subject;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    //TODO change to enum like option
    private String failedDescription;
}
