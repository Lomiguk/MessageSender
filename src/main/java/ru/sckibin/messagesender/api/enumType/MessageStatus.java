package ru.sckibin.messagesender.api.enumType;

/**
 * Enum of message sending statuses
 * <p>
 * IN_QUEUE - The Message is not sent, it is waiting
 * PROCESS - The Process of sending was started, but not completed
 * FAILED - The Process of sending was completed with an error
 * SENT - The process of sending has been completed successfully
 */
public enum MessageStatus {

    IN_QUEUE,
    PROCESS,
    FAILED,
    SENT
}
