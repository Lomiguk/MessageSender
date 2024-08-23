package ru.sckibin.messagesender.service.interfaces;

import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface MessageService {

    Collection<MessageResponse> send(MessageRequest request);

    StatusResponse getStatus(UUID id);

    List<MessageResponse> getMessages(
            String recipient,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer pageSize,
            Integer pageNumber
    );
}
