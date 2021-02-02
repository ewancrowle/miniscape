package net.miniscape.session;

import com.google.gson.Gson;
import com.google.inject.Inject;
import net.miniscape.data.DataManager;
import net.miniscape.server.Server;
import net.miniscape.server.ServerManager;
import net.miniscape.util.Response;
import net.miniscape.util.UuidUtil;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.Map;
import java.util.UUID;

@Manager(name = "Session Manager")
public class SessionManager {
    private final Gson GSON = new Gson();
    private Jedis jedis;

    @Inject
    private ServerManager serverManager;

    @Inject
    public SessionManager(final JedisPool jedisPool) {
        jedis = jedisPool.getResource();
    }

    public Session getSession(UUID uuid) {
        String key = "session:" + UuidUtil.toString(uuid);
        return GSON.fromJson(jedis.get(key), Session.class);
    }

    public Response saveSession(Session session) {
        String key = "session:" + UuidUtil.toString(session.getUuid());

        Pipeline pipeline = jedis.pipelined();
        jedis.set(key, GSON.toJson(session));
        jedis.expire(key, 1800);
        pipeline.sync();

        return Response.success();
    }

    public Response newSession(Player player) {
        Server server = serverManager.getServer();
        Session session = new Session(player.getUniqueId(), System.currentTimeMillis(), "unknown", server.getName(), server.getGameType());
        return saveSession(session);
    }

    public Response newSession(Player player, Map<String, String> connectionProps) {
        Server server = serverManager.getServer();
        Session session = new Session(player.getUniqueId(), System.currentTimeMillis(), "unknown", server.getName(), server.getGameType());
        session.setConnectionProps(connectionProps);
        return saveSession(session);
    }
}