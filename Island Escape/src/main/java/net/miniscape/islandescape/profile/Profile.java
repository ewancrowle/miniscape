package net.miniscape.islandescape.profile;

import lombok.Data;
import net.miniscape.islandescape.clothing.Outfit;
import net.miniscape.islandescape.economy.CurrencyType;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity(value = "islands", noClassnameStored = true)
public class Profile {
    /*
    UUIDs stored here are {@link Profile} IDs.
     */
    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID id;

    @Indexed
    private final UUID player;

    @Indexed
    private final String name;

    private Outfit outfit;

    private Map<CurrencyType, Integer> wallet = new HashMap<>();

    private boolean expandedInventory = false;

    private UUID islandId;
}