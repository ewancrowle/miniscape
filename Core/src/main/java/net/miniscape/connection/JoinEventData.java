package net.miniscape.connection;

import lombok.Data;

import java.util.UUID;

@Data
public class JoinEventData {
    private final UUID player;
    private final String server;
    private final long joined;
}