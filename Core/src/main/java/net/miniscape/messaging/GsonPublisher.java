package net.miniscape.messaging;

import com.google.gson.Gson;
import net.miniscape.util.Response;
import redis.clients.jedis.Jedis;

public class GsonPublisher implements Publisher {
    private final Gson GSON = new Gson();

    private Jedis jedis;

    public GsonPublisher(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Response publish(String channel, Object data) {
        try {
            jedis.publish(channel, GSON.toJson(data));
            return Response.success();
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage());
        }
    }
}
