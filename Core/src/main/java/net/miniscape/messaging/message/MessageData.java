package net.miniscape.messaging.message;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageData {
    private final UUID sender, receiver;
    private final String message;
}
