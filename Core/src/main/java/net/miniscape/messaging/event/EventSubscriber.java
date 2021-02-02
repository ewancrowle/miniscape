package net.miniscape.messaging.event;

import com.google.gson.Gson;
import lombok.Getter;
import net.miniscape.messaging.MessagingManager;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.Map;

public class EventSubscriber extends JedisPubSub {
    private final Gson GSON = new Gson();

    @Getter
    private Map<EventType, EventCallback> callbackMap = new HashMap<>();

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equalsIgnoreCase(MessagingManager.EVENT_CHANNEL_NAME)) {
            return;
        }

        Event event = GSON.fromJson(message, Event.class);

        if (event != null) {
            EventCallback callback = callbackMap.get(event.getEventType());

            if (callback != null) {
                callback.execute(event.getData());
            }
        }
    }
}