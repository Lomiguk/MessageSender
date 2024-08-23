package ru.sckibin.messagesender.service.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sckibin.messagesender.api.dto.MessageDTO;

@Service
@RequiredArgsConstructor
public class TextSenderEmailImpl implements TextSender {

    public final JavaMailSender mailSender;
    @Value("${spring.mail.sender}")
    private String senderEmailAddress;

    @Async
    @Override
    public void sendTextMessage(MessageDTO message) {
        sendTextMessage(message.getRecipient(), message.getSubject(), message.getBody());
    }

    private void sendTextMessage(String recipient, String subject, String body) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmailAddress);
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }
}
