package net.miniscape.messaging.sync;

import net.miniscape.messaging.GsonPublisher;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.messaging.event.Event;
import net.miniscape.util.Response;
import redis.clients.jedis.Jedis;

public class SyncPublisher extends GsonPublisher {
    public SyncPublisher(Jedis jedis) {
        super(jedis);
    }

    public Response publish(Event event) {
        return super.publish(MessagingManager.SYNC_CHANNEL_NAME, event);
    }
}