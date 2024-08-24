package ru.sckibin.messagesender.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sckibin.messagesender.api.dto.MessageDTO;
import ru.sckibin.messagesender.api.enumType.MessageStatus;
import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;
import ru.sckibin.messagesender.entity.Message;
import ru.sckibin.messagesender.exception.ExpectedEntityNotFoundException;
import ru.sckibin.messagesender.exception.UnrealizedMethodOfSendingException;
import ru.sckibin.messagesender.repository.MessageRepository;
import ru.sckibin.messagesender.repository.util.MessageSpecification;
import ru.sckibin.messagesender.service.interfaces.MessageService;
import ru.sckibin.messagesender.service.sender.TextSender;
import ru.sckibin.messagesender.util.MessageUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class.getName());
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 15;

    private final MessageRepository messageRepository;
    @Qualifier("emailSender")
    private final TextSender emailSender;
    private final MessageUtil messageUtil;

    @Override
    public Collection<MessageResponse> send(MessageRequest request) {
        var result = new ArrayList<MessageResponse>();

        for(var type : request.getType()) {
            var message = messageUtil.mapToDTO(request, type);
            try {
                setMessageStatus(message, MessageStatus.IN_QUEUE);
                sendMessage(message);
            } catch (RuntimeException ex) {
                LOGGER.info(
                        String.format("Message (%s) was not sent, exception %s", message.getId(), ex.getMessage())
                );

                setMessageStatus(message, MessageStatus.FAILED);
                //TODO change to normal response, for example HTTP status code
                message.setFailedDescription(ex.getMessage());
            } finally {
                result.add(messageUtil.mapToMessageResponse(message));
            }
        }

        return result;
    }

    @Override
    public StatusResponse getStatus(UUID id) {
        return messageRepository.findById(id)
                .map(messageUtil::mapToStatusResponse)
                .orElseThrow(
                        () -> new ExpectedEntityNotFoundException(String.format("Message with id = %s not found", id))
                );
    }

    @Override
    public List<MessageResponse> getMessages(
            String recipient,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer pageSize,
            Integer pageNumber
    ) {
        Specification<Message> spec = Specification.where(null);
        if (recipient != null && !recipient.isEmpty()) {
            spec = spec.and(MessageSpecification.hasRecipient(recipient));
        }
        if (startDate != null) {
            spec = spec.and(MessageSpecification.createdAfter(startDate));
        }
        if (endDate != null) {
            spec = spec.and(MessageSpecification.createdBefore(endDate));
        }

        pageNumber = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        return messageRepository.findAll(spec, PageRequest.of(pageNumber, pageSize)).stream()
                .map(messageUtil::mapToMessageResponse)
                .toList();
    }

    private void sendMessage(MessageDTO message) {
        switch (message.getType()) {
            case EMAIL -> sendEmail(message);
            /* case SMS -> {

            }  */
            default -> throw new UnrealizedMethodOfSendingException(
                    String.format("This type (%s) of message sending is unrealized", message.getType())
            );
        }
    }

    private void sendEmail(MessageDTO message) {
        setMessageStatus(message, MessageStatus.PROCESS);
        emailSender.sendTextMessage(message);
    }

    private void setMessageStatus(MessageDTO message, MessageStatus status) {
        message.setStatus(status);
        messageRepository.save(messageUtil.mapToEntity(message));
    }
}
