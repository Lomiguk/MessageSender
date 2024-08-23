package ru.sckibin.messagesender.service.sender;

import ru.sckibin.messagesender.api.dto.MessageDTO;

public interface TextSender {

    void sendTextMessage(MessageDTO message);
}
