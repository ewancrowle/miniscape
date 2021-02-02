package net.miniscape.friend;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;

import java.util.UUID;

@Data
@Entity(value = "friendships", noClassnameStored = true)
public class Friendship {
    private final UUID sender, receiver;
    private long started = 0;
}