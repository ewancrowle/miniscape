package net.miniscape.connection;

import com.google.inject.Inject;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.messaging.event.Event;
import net.miniscape.messaging.event.EventType;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.PlayerManager;
import net.miniscape.player.Players;
import net.miniscape.server.ServerManager;
import net.miniscape.session.Session;
import net.miniscape.session.SessionManager;
import net.miniscape.session.Sessions;
import net.miniscape.stats.StatsManager;
import net.miniscape.translation.Translations;
import net.miniscape.util.environment.Environment;
import net.miniscape.util.guice.Manager;
import net.miniscape.util.log.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Manager(name = "Connection Manager")
public class ConnectionManager {
    @Inject
    private PlayerManager playerManager;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private MessagingManager messagingManager;

    @Inject
    private ServerManager serverManager;

    @Inject
    private StatsManager statsManager;

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        long time = System.currentTimeMillis();

        Player player = event.getPlayer();

        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        NetworkPlayer np = playerOptional.get();

        if (!playerOptional.isPresent()) {
            np = new NetworkPlayer(player.getUniqueId(), time);
        }

        np.setName(player.getName());

        if (!np.getKnownNameHistory().contains(player.getName())) {
            np.getKnownNameHistory().add(player.getName());
        }

        np.setLastLogin(time);

        playerManager.saveNetworkPlayer(np);

        Optional<Session> optionalSession = Sessions.of(player).optional();
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            Map<String, String> connectionProps = session.getConnectionProps();

			/*
			Handle data transferred with a player
			across servers, such as their target.
			 */

            sessionManager.newSession(player, connectionProps);
        } else {
            sessionManager.newSession(player);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        long joined = System.currentTimeMillis();
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        JoinEventData data = new JoinEventData(player.getUniqueId(), serverManager.getServer().getName(), joined);

        messagingManager.getEventPublisher().publish(new Event(EventType.JOIN, data));

        if (serverManager.getServer().getEnvironment() == Environment.DEV) {
            Log.to(player).warn(Translations.to(player).get("BUG_WARNING"));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Optional<NetworkPlayer> np = Players.of(player).optional();

        if (np.isPresent()) {
            np.get().setLastSeen(System.currentTimeMillis());
            Players.of(np.get()).save();
        }

        Optional<Session> optionalSession = Sessions.of(player).optional();
        if (optionalSession.isPresent()) {
            Session session = optionalSession.get();
            session.setOnline(false);

            Map<String, String> connectionProps = new HashMap<>();
			/*
			Set all data transferred with a player
			across servers, such as their target.
			 */
            session.setConnectionProps(connectionProps);

            sessionManager.saveSession(session);
        }
    }
}