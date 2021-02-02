package net.miniscape.session;

import com.google.inject.Inject;
import net.miniscape.player.NetworkPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class Sessions {
    @Inject
    private SessionManager sessionManager;

    private Session session;

    private Sessions(Player player) {
        this.session = sessionManager.getSession(player.getUniqueId());
    }

    private Sessions(UUID uuid) {
        this.session = sessionManager.getSession(uuid);
    }

    private Sessions(String name) {
        this.session = sessionManager.getSession(Bukkit.getPlayer(name).getUniqueId());
    }

    public Sessions(NetworkPlayer player) {
        this.session = sessionManager.getSession(player.getUuid());
    }

    public static Sessions of(Player player) {
        return new Sessions(player);
    }

    public static Sessions of(UUID uuid) {
        return new Sessions(uuid);
    }

    public static Sessions of(String name) {
        return new Sessions(name);
    }

    public static Sessions of(NetworkPlayer player) {
        return new Sessions(player);
    }

    public Optional<Session> optional() {
        return Optional.ofNullable(session);
    }
}
