package ru.sckibin.messagesender.util;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sckibin.messagesender.api.dto.MessageDTO;
import ru.sckibin.messagesender.api.enumType.MessageFailedCode;
import ru.sckibin.messagesender.api.enumType.MessageStatus;
import ru.sckibin.messagesender.api.enumType.MessageType;
import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.FailedDescription;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;
import ru.sckibin.messagesender.entity.Message;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final ModelMapper modelMapper;

    public MessageResponse mapToMessageResponse(Message message) {
        return modelMapper.map(message, MessageResponse.class);
    }

    public MessageResponse mapToMessageResponse(MessageDTO message) {
        return modelMapper.map(message, MessageResponse.class);
    }

    public StatusResponse mapToStatusResponse(Message message) {
        return modelMapper.map(message, StatusResponse.class);
    }

    public MessageDTO mapToDTO(MessageRequest messageRequest, MessageType type) {
        return switch (type) {
            case EMAIL -> mapToEmailEntity(messageRequest);
            default -> mapToDefaultEntity(messageRequest);
        };
    }

    public Message mapToEntity(MessageDTO dto) {
        return modelMapper.map(dto, Message.class);
    }

    public FailedDescription mapToDescription(MessageFailedCode code) {
        return new FailedDescription(
                code.code,
                code.header
        );
    }

    private MessageDTO mapToEmailEntity(MessageRequest messageRequest) {
        return MessageDTO.builder()
                .id(UUID.randomUUID())
                .recipient(messageRequest.getRecipient())
                .type(MessageType.EMAIL)
                .status(MessageStatus.IN_QUEUE)
                .subject(messageRequest.getSubject())
                .body(messageRequest.getText())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private MessageDTO mapToDefaultEntity(MessageRequest messageRequest) {
        return MessageDTO.builder()
                .id(UUID.randomUUID())
                .recipient(messageRequest.getRecipient())
                .type(MessageType.UNREALIZED)
                .status(MessageStatus.FAILED)
                .body(messageRequest.getText())
                .createdAt(LocalDateTime.now())
                .failedDescription(mapToDescription(MessageFailedCode.UNREALIZED_TYPE_EXCEPTION))
                .build();
    }
}
