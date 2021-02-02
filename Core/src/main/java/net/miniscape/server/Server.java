package net.miniscape.server;

import lombok.Data;
import net.miniscape.data.redis.RedisData;
import net.miniscape.game.GameType;
import net.miniscape.util.environment.Environment;

@Data
public class Server implements RedisData {
    private final String name;
    private final String address;

    private final GameType gameType;
    private final Environment environment;
    private String map;
    private Object mode;

    private int players;

    @Override
    public String getKey() {
        return "server:" + name;
    }
}