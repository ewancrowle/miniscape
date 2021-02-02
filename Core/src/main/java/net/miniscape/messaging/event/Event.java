package net.miniscape.messaging.event;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;

@Data
@Entity(value = "events", noClassnameStored = true)
public class Event {
    private final EventType eventType;
    private final Object data;
}