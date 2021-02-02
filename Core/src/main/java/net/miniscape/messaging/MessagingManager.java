package net.miniscape.messaging;

import com.google.inject.Inject;
import lombok.Getter;
import net.miniscape.chat.ChatCallback;
import net.miniscape.messaging.event.*;
import net.miniscape.messaging.message.MessageData;
import net.miniscape.messaging.message.MessageCallback;
import net.miniscape.messaging.sync.SyncPublisher;
import net.miniscape.messaging.sync.SyncSubscriber;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.session.Session;
import net.miniscape.session.SessionManager;
import net.miniscape.translation.Translations;
import net.miniscape.util.Response;
import net.miniscape.util.guice.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.mongodb.morphia.Datastore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Manager(name = "Messaging Manager")
public class MessagingManager extends JedisPubSub {
    public static final String SYNC_CHANNEL_NAME = "sync";
    public static final String EVENT_CHANNEL_NAME = "events";

    @Inject
    private SessionManager sessionManager;

    @Inject
    private Plugin plugin;

    @Getter
    private EventPublisher eventPublisher;

    @Getter
    private SyncPublisher syncPublisher;

    @Getter
    private EventSubscriber eventSubscriber;

    @Getter
    private SyncSubscriber syncSubscriber;

    @Inject
    private Datastore datastore;

    @Inject
    private JedisPool jedisPool;

    private Jedis jedis;

    public MessagingManager() {
        jedis = jedisPool.getResource();

        eventPublisher = new EventPublisher(jedis, datastore);
        syncPublisher = new SyncPublisher(jedis);

        eventSubscriber = new EventSubscriber();
        syncSubscriber = new SyncSubscriber();

        setEventCallback(EventType.MESSAGE, new MessageCallback());
        setEventCallback(EventType.CHAT, new ChatCallback());

        subscribe(eventSubscriber, EVENT_CHANNEL_NAME);
        subscribe(syncSubscriber, SYNC_CHANNEL_NAME);
    }

    public void setEventCallback(EventType type, EventCallback service) {
        eventSubscriber.getCallbackMap().put(type, service);
    }

    public void subscribe(JedisPubSub subscriber, String channel) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                plugin.getLogger().info("Subscribing " + subscriber.getClass().getSimpleName() + "to channel \"" + channel + "\". This thread will be blocked.");
                jedis.subscribe(subscriber, channel);
                plugin.getLogger().info("Subscription ended.");
            } catch (Exception e) {
                plugin.getLogger().severe("Subscribing failed.");
                e.printStackTrace();
            }
        });
    }

    public Response sendMessage(Player player, NetworkPlayer target, String message) {
        Session session = sessionManager.getSession(target.getUuid());
        if (session == null) {
            return new Response(false, Translations.to(player).get("MESSAGE_FAILED_OFFLINE"));
        }

        String name = target.getName();

        if (session.isOnline()) {
            Response response = eventPublisher.publish(new Event(EventType.MESSAGE, new MessageData(player.getUniqueId(), target.getUuid(), message)));
            Translations.to(player).sendMessage("MESSAGE_SENT", name, message);
            return response;
        }

        return new Response(false, Translations.to(player).get("MESSAGE_FAILED"));
    }
}