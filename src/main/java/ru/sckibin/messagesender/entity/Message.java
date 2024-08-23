package ru.sckibin.messagesender.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sckibin.messagesender.api.enumType.MessageStatus;
import ru.sckibin.messagesender.api.enumType.MessageType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {

    @Id
    private UUID id;
    private String recipient;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    private String subject;
    private String body;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
