package ru.sckibin.messagesender.api.request;

import lombok.Data;
import ru.sckibin.messagesender.api.enumType.MessageType;

@Data
public class MessageRequest {
    private String recipient;
    private String subject;
    private MessageType type;
    private String text;
}
