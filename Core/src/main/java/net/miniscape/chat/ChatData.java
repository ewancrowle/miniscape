package net.miniscape.chat;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatData {
    private final String server;
    private final ChatChannel chatChannel;
    private final UUID player;
    private final String message;
    private final long time;
}
