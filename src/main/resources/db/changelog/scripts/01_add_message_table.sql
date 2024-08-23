CREATE TABLE message
(
    id         UUID         PRIMARY KEY,
    recipient  VARCHAR(255) NOT NULL,
    type       VARCHAR(50)  NOT NULL,
    status     VARCHAR(50)  NOT NULL,
    subject    VARCHAR(255),
    body       TEXT         NOT NULL,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT message_type_check check ( type IN ('EMAIL') )
);

COMMENT ON COLUMN message.id IS 'Unique profile identifier';
COMMENT ON COLUMN message.recipient IS 'The reference to the recipient';
COMMENT ON COLUMN message.type IS 'Type of message, like email, sms, etc.';
COMMENT ON COLUMN message.status IS 'The status of the message in the process of sending';
COMMENT ON COLUMN message.subject IS 'The attribute for email message, a brief preview or headline for the content of an email';
COMMENT ON COLUMN message.body IS 'Message content';
COMMENT ON COLUMN message.created_at IS 'The Date with time when the message was created';
COMMENT ON COLUMN message.updated_at IS 'The Date with time when the message was updated';