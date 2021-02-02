package net.miniscape.preferences;

import com.google.inject.Inject;
import net.miniscape.util.Response;
import net.miniscape.util.UuidUtil;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;

import java.util.UUID;

@Manager(name = "Preferences Manager")
public class PreferencesManager {
    @Inject
    private Datastore datastore;

    public PreferencesToken getPreferencesTokenByUuid(UUID uuid) {
        return datastore.find(PreferencesToken.class).field("uuid").equal(UuidUtil.toString(uuid)).get();
    }

    public PreferencesToken getPreferencesTokenByPlayer(Player player) {
        return getPreferencesTokenByUuid(player.getUniqueId());
    }

    public PreferencesToken getStatsTokenByName(String name) {
        return datastore.find(PreferencesToken.class).field("name").equalIgnoreCase(name).get();
    }

    public Response savePreferencesToken(PreferencesToken token) {
        datastore.save(token);
        return Response.success();
    }
}
