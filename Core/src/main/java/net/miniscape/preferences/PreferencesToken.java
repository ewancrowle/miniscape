package net.miniscape.preferences;


import lombok.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Entity(value = "preferences", noClassnameStored = true)
public class PreferencesToken {
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID uuid;

    private Map<PreferenceType, Object> preferences = new HashMap<>();
}