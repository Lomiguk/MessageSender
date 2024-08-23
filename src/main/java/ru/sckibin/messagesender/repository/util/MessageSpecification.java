package ru.sckibin.messagesender.repository.util;

import org.springframework.data.jpa.domain.Specification;
import ru.sckibin.messagesender.entity.Message;

import java.time.LocalDateTime;

public class MessageSpecification {

    public static Specification<Message> hasRecipient(String recipient) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("recipient"), recipient);
    }

    public static Specification<Message> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    }


    public static Specification<Message> createdBefore(LocalDateTime endDate) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
    }
}
