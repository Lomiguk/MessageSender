package ru.sckibin.messagesender.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/messages")
public class MessageStatusController {

    //todo MessageService

    @PostMapping
    public ResponseEntity<MessageResponse> send(
            @RequestBody
            MessageRequest request
    ) {
        //TODO
        return null;
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<StatusResponse> getResponse(
            @PathVariable UUID id
    ) {
        //TODO
        return null;
    }

    @GetMapping
    public ResponseEntity<Collection<MessageResponse>> getHistory() {
        //TODO
        return null;
    }
}
