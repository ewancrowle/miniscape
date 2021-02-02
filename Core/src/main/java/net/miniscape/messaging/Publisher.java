package net.miniscape.messaging;

import net.miniscape.util.Response;
import redis.clients.jedis.Jedis;

public interface Publisher {
    static Publisher createDefault(Jedis jedis) {
        return new GsonPublisher(jedis);
    }

    Response publish(String channel, Object data);
}