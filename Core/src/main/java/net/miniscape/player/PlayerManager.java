package net.miniscape.player;

import com.google.inject.Inject;
import net.miniscape.data.DataManager;
import net.miniscape.util.Response;
import net.miniscape.util.UuidUtil;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;

import java.util.UUID;

@Manager(name = "Player Manager")
public class PlayerManager {
    @Inject
    private Datastore datastore;

    public NetworkPlayer getNetworkPlayerByUuid(UUID uuid) {
        return datastore.find(NetworkPlayer.class).field("uuid").equal(UuidUtil.toString(uuid)).get();
    }

    public NetworkPlayer getNetworkPlayerByPlayer(Player player) {
        return getNetworkPlayerByUuid(player.getUniqueId());
    }

    public NetworkPlayer getNetworkPlayerByName(String name) {
        return datastore.find(NetworkPlayer.class).field("name").equalIgnoreCase(name).get();
    }

    public Response saveNetworkPlayer(NetworkPlayer np) {
        datastore.save(np);
        return Response.success();
    }
}