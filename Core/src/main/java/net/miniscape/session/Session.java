package net.miniscape.session;

import lombok.Data;
import net.miniscape.data.redis.RedisData;
import net.miniscape.game.GameType;
import net.miniscape.util.UuidUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class Session implements RedisData {
    private final UUID uuid;

    private boolean online = true;
    private final long started;

    private final String proxy;
    private final String server;
    private final GameType gameType;

    private Map<String, String> connectionProps = new HashMap<>();

    @Override
    public String getKey() {
        return "session:" + UuidUtil.toString(uuid);
    }
}