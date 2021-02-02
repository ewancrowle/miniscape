package net.miniscape.messaging.event;

import net.miniscape.messaging.GsonPublisher;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.util.Response;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.Jedis;

public class EventPublisher extends GsonPublisher {
    private Datastore datastore;

    public EventPublisher(Jedis jedis, Datastore datastore) {
        super(jedis);
        this.datastore = datastore;
    }

    public Response publish(Event event) {
        datastore.save(event);
        return super.publish(MessagingManager.EVENT_CHANNEL_NAME, event);
    }
}