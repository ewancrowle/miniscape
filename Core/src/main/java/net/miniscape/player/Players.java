package net.miniscape.player;

import com.google.inject.Inject;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class Players {
    @Inject
    private PlayerManager playerManager;

    private NetworkPlayer player;

    private Players(Player player) {
        this.player = playerManager.getNetworkPlayerByPlayer(player);
    }

    private Players(UUID uuid) {
        this.player = playerManager.getNetworkPlayerByUuid(uuid);
    }

    private Players(String name) {
        this.player = playerManager.getNetworkPlayerByName(name);
    }

    public Players(NetworkPlayer player) {
        this.player = player;
    }

    public static Players of(Player player) {
        return new Players(player);
    }

    public static Players of(UUID uuid) {
        return new Players(uuid);
    }

    public static Players of(String name) {
        return new Players(name);
    }

    public static Players of(NetworkPlayer player) {
        return new Players(player);
    }

    public Optional<NetworkPlayer> optional() {
        return Optional.ofNullable(player);
    }

    public Optional<String> name() {
        if (player == null) {
            return Optional.empty();
        }
        return Optional.of(player.getName());
    }

    public Players save() {
        playerManager.saveNetworkPlayer(player);
        return this;
    }
}
