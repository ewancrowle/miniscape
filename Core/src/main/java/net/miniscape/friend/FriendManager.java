package net.miniscape.friend;

import com.google.inject.Inject;
import net.miniscape.data.DataManager;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.util.Response;
import net.miniscape.util.guice.Manager;
import org.bukkit.entity.Player;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;
import java.util.UUID;

@Manager(name = "Friend Manager")
public class FriendManager {
    @Inject
    private Datastore datastore;

    public List<Friendship> getOutboundRequests(NetworkPlayer player) {
        return datastore.find(Friendship.class).field("sender").equal(player.getUuid()).field("started").equal(0).asList();
    }

    public List<Friendship> getInboundRequests(NetworkPlayer player) {
        return datastore.find(Friendship.class).field("receiver").equal(player.getUuid()).field("started").equal(0).asList();
    }

    public List<Friendship> getFriends(NetworkPlayer player) {
        Query<Friendship> query = datastore.find(Friendship.class);
        query.or(query.criteria("sender").equal(player.getUuid()), query.criteria("receiver").equal(player.getUuid())).criteria("started").notEqual(0);
        return query.asList();
    }

    public Response sendRequest(Player from, UUID to) {
        datastore.save(new Friendship(from.getUniqueId(), to));
        return new Response(true, "REQUEST_SENT");
    }

    public Response acceptRequest(Friendship request) {
        request.setStarted(System.currentTimeMillis());
        datastore.save(request);
        return new Response(true, "FRIEND_ACCEPT");
    }

    public Response cancelRequest(Friendship request) {
        datastore.delete(request);
        return new Response(false, "REQUEST_CANCEL");
    }

    public Response endFriendship(Friendship friendship) {
        datastore.delete(friendship);
        return new Response(false, "FRIEND_END");
    }

    public boolean isFriendship(UUID uniqueId, UUID uuid) {
        return true;
    }
}
