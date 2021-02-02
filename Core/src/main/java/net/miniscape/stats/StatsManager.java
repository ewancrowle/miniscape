package net.miniscape.stats;

import com.google.inject.Inject;
import net.miniscape.data.DataManager;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.util.Response;
import net.miniscape.util.UuidUtil;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;

import java.util.UUID;

@Manager(name = "Stats Manager")
public class StatsManager {
    @Inject
    private Datastore datastore;

    public StatsToken getStatsTokenByUuid(UUID uuid) {
        return datastore.find(StatsToken.class).field("uuid").equal(UuidUtil.toString(uuid)).get();
    }

    public StatsToken getStatsTokenByPlayer(Player player) {
        return getStatsTokenByUuid(player.getUniqueId());
    }

    public StatsToken getStatsTokenByName(String name) {
        return datastore.find(StatsToken.class).field("name").equalIgnoreCase(name).get();
    }

    public Response saveStatsToken(StatsToken token) {
        datastore.save(token);
        return Response.success();
    }
}
