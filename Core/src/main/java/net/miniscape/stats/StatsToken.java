package net.miniscape.stats;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity(value = "stats", noClassnameStored = true)
public class StatsToken {
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID uuid;

    private Map<String, Map<String, Integer>> stats = new HashMap<>();
}