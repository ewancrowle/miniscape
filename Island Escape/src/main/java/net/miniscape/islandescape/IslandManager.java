package net.miniscape.islandescape;

import com.google.inject.Inject;
import lombok.Getter;
import net.miniscape.islandescape.profile.Profile;
import net.miniscape.islandescape.profile.Profiles;
import net.miniscape.session.Session;
import net.miniscape.session.Sessions;
import net.miniscape.util.Response;
import net.miniscape.util.UuidUtil;
import net.miniscape.util.environment.EnvironmentProvider;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;
import java.util.UUID;

@Getter
@Manager(name = "Island Manager")
public class IslandManager {
    private final int maximumIslands = Integer.parseInt(new EnvironmentProvider("MAX_ISLANDS").get());
    private Jedis jedis;

    @Inject
    private Datastore datastore;

    @Inject
    public IslandManager(final JedisPool jedisPool) {
        jedis = jedisPool.getResource();
    }

    public Optional<Island> getIslandByProfileId(UUID profileId) {
        UUID islandId = UuidUtil.parseUUID(jedis.get("profile:" + UuidUtil.toString(profileId) + ":island"));
        if (islandId != null) {
            Island island = getIsland(islandId);
            return Optional.ofNullable(island);
        }

        Optional<Profile> profileOptional = Profiles.of(profileId).optional();
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            Island island = getIsland(profile.getIslandId());
            return Optional.ofNullable(island);
        }

        return Optional.empty();
    }

    public Island getIsland(UUID islandId) {
        return datastore.find(Island.class).field("id").equal(islandId).get();
    }

    public void joinIsland(Profile profile, Island island) {
        profile.setIslandId(island.getId());
        getDatastore().save(profile);
        jedis.set("profile:" + UuidUtil.toString(profile.getId()) + ":island", UuidUtil.toString(island.getId()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Optional<Session> optionalSession = Sessions.of(player).optional();
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            if (session.getConnectionProps().containsKey("profile")) {
                Profile profile = Profiles.of(UuidUtil.parseUUID(session.getConnectionProps().get("profile"))).optional().get();
                return;
            }

            player.kickPlayer(new Response(false, "Missing profile data.").getMessage());
        }
        player.kickPlayer(new Response(false, "Missing session data.").getMessage());
    }
}