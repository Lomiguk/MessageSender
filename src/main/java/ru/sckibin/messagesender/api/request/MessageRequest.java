package ru.sckibin.messagesender.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.sckibin.messagesender.api.enumType.MessageType;

import java.util.Set;

@Data
public class MessageRequest {

    @NotBlank(message = "Recipient is mandatory")
    private String recipient;
    private String subject;
    @NotEmpty(message = "Type list must not be empty")
    private Set<MessageType> type;
    @NotBlank(message = "Body is mandatory")
    private String text;
}
