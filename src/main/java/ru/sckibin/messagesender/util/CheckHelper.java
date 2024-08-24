package ru.sckibin.messagesender.util;

import org.springframework.stereotype.Component;
import ru.sckibin.messagesender.api.dto.MessageDTO;
import ru.sckibin.messagesender.exception.ValidationException;

import java.util.regex.Pattern;

@Component
public class CheckHelper {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                                              "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public void checkEmailMessage(MessageDTO message) {
        var pat = Pattern.compile(EMAIL_REGEX);
        if (message.getRecipient() == null ||
            !pat.matcher(message.getRecipient()).matches()){
            throw new ValidationException("Wrong recipient email");
        }
        if (message.getSubject() == null) {
            throw new ValidationException("Subject is null");
        }
    }
}
