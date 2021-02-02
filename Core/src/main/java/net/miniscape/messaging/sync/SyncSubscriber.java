package net.miniscape.messaging.sync;

import com.google.gson.Gson;
import net.miniscape.messaging.MessagingManager;
import redis.clients.jedis.JedisPubSub;

public class SyncSubscriber extends JedisPubSub {
    private final Gson GSON = new Gson();

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equalsIgnoreCase(MessagingManager.SYNC_CHANNEL_NAME)) {
            return;
        }
    }
}