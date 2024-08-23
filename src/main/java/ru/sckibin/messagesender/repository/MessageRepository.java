package ru.sckibin.messagesender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.sckibin.messagesender.entity.Message;

import java.util.UUID;

@Repository
public interface MessageRepository
        extends JpaRepository<Message, UUID>,
                JpaSpecificationExecutor<Message> {
}
