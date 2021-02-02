package net.miniscape.islandescape;

import lombok.Data;
import net.miniscape.islandescape.event.IslandEvent;
import net.miniscape.islandescape.util.phase.Phase;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(value = "islands", noClassnameStored = true)
public class Island {
    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID id;

    @Indexed(options = @IndexOptions(unique = true))
    private final UUID leaderProfile;

    @Indexed
    private final String name;
    private final long founded;

    private List<IslandEvent> history = new ArrayList<>();
    private int visitorCount = 0;

    private List<UUID> residentProfiles = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();

    private List<Phase> tutorial;
}