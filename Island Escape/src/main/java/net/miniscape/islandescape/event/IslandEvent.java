package net.miniscape.islandescape.event;

import lombok.Data;

@Data
public class IslandEvent {
    private final IslandEventType type;
    private final String value;
}
