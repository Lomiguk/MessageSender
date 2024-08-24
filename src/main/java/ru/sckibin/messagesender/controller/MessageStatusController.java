package ru.sckibin.messagesender.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sckibin.messagesender.api.request.MessageRequest;
import ru.sckibin.messagesender.api.response.MessageResponse;
import ru.sckibin.messagesender.api.response.StatusResponse;
import ru.sckibin.messagesender.service.interfaces.MessageService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageStatusController {

    private final MessageService messageService;

    @PostMapping
    @Operation(summary = "Sending message")
    public ResponseEntity<Collection<MessageResponse>> send(
            @Valid
            @RequestBody
            MessageRequest request
    ) {
        return new ResponseEntity<>(
                messageService.send(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}/status")
    @Operation(summary = "Getting message status")
    public ResponseEntity<StatusResponse> getStatus(
            @PathVariable UUID id
    ) {
        return new ResponseEntity<>(
                messageService.getStatus(id),
                HttpStatus.OK
        );
    }

    @GetMapping
    @Operation(summary = "Getting message history")
    public ResponseEntity<List<MessageResponse>> getHistory(
            @RequestParam(required = false)
            String recipient,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate,
            @PositiveOrZero
            @RequestParam(required = false)
            Integer limit,
            @PositiveOrZero
            @RequestParam(required = false)
            Integer offset
    ) {
        return new ResponseEntity<>(
                messageService.getMessages(recipient, startDate, endDate, limit, offset),
                HttpStatus.OK
        );
    }
}
