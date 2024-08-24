package ru.sckibin.messagesender.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import ru.sckibin.messagesender.api.dto.MessageDTO;
import ru.sckibin.messagesender.api.enumType.MessageFailedCode;
import ru.sckibin.messagesender.api.enumType.MessageStatus;
import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;
import ru.sckibin.messagesender.entity.Message;
import ru.sckibin.messagesender.exception.ExpectedEntityNotFoundException;
import ru.sckibin.messagesender.exception.UnrealizedMethodOfSendingException;
import ru.sckibin.messagesender.exception.ValidationException;
import ru.sckibin.messagesender.repository.MessageRepository;
import ru.sckibin.messagesender.repository.util.MessageSpecification;
import ru.sckibin.messagesender.service.interfaces.MessageService;
import ru.sckibin.messagesender.service.sender.TextSender;
import ru.sckibin.messagesender.util.CheckHelper;
import ru.sckibin.messagesender.util.MessageUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = Logger.getLogger(MessageServiceImpl.class.getName());
    // TODO Get from property
    private static final int DEFAULT_PAGE_NUMBER = 0;
    // TODO Get from property
    private static final int DEFAULT_PAGE_SIZE = 15;

    private final MessageRepository messageRepository;
    @Qualifier("emailSender")
    private final TextSender emailSender;
    private final MessageUtil messageUtil;
    private final CheckHelper checkHelper;

    @Override
    public Collection<MessageResponse> send(MessageRequest request) {
        var result = new ArrayList<MessageResponse>();

        for (var type : request.getType()) {
            var message = messageUtil.mapToDTO(request, type);
            result.add(sendProcessing(message));
        }

        // Getting actual Data
        return result.stream()
                .map((m) -> messageRepository.findById(m.getMessageId()))
                .flatMap(Optional::stream)
                .map(messageUtil::mapToMessageResponse)
                .toList();
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
            case EMAIL -> {
                checkHelper.checkEmailMessage(message);
                sendEmail(message);
            }
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

    private void failedMessage(MessageDTO message, MessageFailedCode messageFailedCode) {
        setMessageStatus(message, MessageStatus.FAILED);
        message.setFailedDescription(messageUtil.mapToDescription(messageFailedCode));
    }

    private MessageResponse sendProcessing(MessageDTO message) {
        try {
            setMessageStatus(message, MessageStatus.IN_QUEUE);
            sendMessage(message);
        } catch (UnrealizedMethodOfSendingException ex) {
            exceptionProcess(message, ex, MessageFailedCode.UNREALIZED_TYPE_EXCEPTION);
        } catch (MailException ex) {
            exceptionProcess(message, ex, MessageFailedCode.MAIL_EXCEPTION);
        } catch (IllegalArgumentException ex) {
            exceptionProcess(message, ex, MessageFailedCode.DB_EXCEPTION);
        } catch (ValidationException ex) {
            exceptionProcess(message, ex, MessageFailedCode.VALIDATION_EXCEPTION);
        } catch (Exception ex) {
            exceptionProcess(message, ex, MessageFailedCode.UNEXPECTED_EXCEPTION);
        }

        return messageUtil.mapToMessageResponse(message);
    }

    private void exceptionProcess(MessageDTO message, Exception ex, MessageFailedCode code) {
        LOGGER.info(
                String.format("Message (%s) was not sent, exception %s", message.getId(), ex.getMessage())
        );

        failedMessage(message, code);
    }
}
