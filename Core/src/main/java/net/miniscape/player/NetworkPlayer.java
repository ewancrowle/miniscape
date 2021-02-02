package net.miniscape.player;

import lombok.Data;
import net.miniscape.game.GameType;
import net.miniscape.translation.Language;
import net.miniscape.util.log.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.*;

@Data
@Entity(value = "players", noClassnameStored = true)
public class NetworkPlayer {
    @Indexed(options = @IndexOptions(unique = true))
    private final UUID uuid;

    private String name;
    private List<String> knownNameHistory = new ArrayList<>();

    private Rank rank = null;

    private final long firstLogin;
    private long lastLogin;
    private long lastSeen;

    private GameType lastGameType;

    private Language language = Language.ENGLISH;

    private List<UUID> blocked = new ArrayList<>();

    private LogLevel logLevel = LogLevel.OFF;

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isRanked() {
        return rank != null;
    }

    public boolean exceeds(Rank comparison) {
        if (!isRanked()) {
            return false;
        }
        return rank.compareTo(comparison) <= 0;
    }

    public boolean isStaff() {
        return exceeds(Rank.HELPER);
    }
}