package net.miniscape.util.log;

import com.google.inject.Inject;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.PlayerManager;
import net.miniscape.player.Players;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Log {
    @Inject
    private static PlayerManager playerManager;

    private Player player;

    private Log(Player player) {
        this.player = player;
    }

    public static Log to(Player player) {
        return new Log(player);
    }

    public LogLevel getLevel() {
        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        if (playerOptional.isPresent()) {
            NetworkPlayer player = playerOptional.get();
            return player.getLogLevel();
        }
        return LogLevel.OFF;
    }

    public void debug(String message) {
        if (getLevel() == LogLevel.ALL || getLevel() == LogLevel.DEBUG) {
            player.sendMessage(ChatColor.DARK_GRAY + "[DEBUG] " + ChatColor.GRAY + message);
        }
    }

    public void info(String message) {
        if (getLevel() == LogLevel.ALL || getLevel() == LogLevel.DEBUG || getLevel() == LogLevel.INFO) {
            player.sendMessage(ChatColor.GRAY + "[INFO] " + message);
        }
    }

    public void warn(String message) {
        if (getLevel() == LogLevel.ALL || getLevel() == LogLevel.DEBUG || getLevel() == LogLevel.INFO || getLevel() == LogLevel.WARN) {
            player.sendMessage(ChatColor.YELLOW + "[WARN] " + ChatColor.GRAY + message);
        }
    }

    public void error(String message) {
        if (getLevel() == LogLevel.ALL || getLevel() == LogLevel.DEBUG || getLevel() == LogLevel.INFO || getLevel() == LogLevel.WARN || getLevel() == LogLevel.FATAL) {
            player.sendMessage(ChatColor.RED + "[ERROR] " + ChatColor.GRAY + message);
        }
    }
}