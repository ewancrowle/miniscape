package net.miniscape.stats;

import com.google.inject.Inject;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class Stats {
    @Inject
    private StatsManager statsManager;

    private Player player;

    private Stats(Player player) {
        this.player = player;
    }

    public static Stats of(Player player) {
        return new Stats(player);
    }

    public Optional<Integer> get(String category, String stat) {
        try {
            StatsToken token = statsManager.getStatsTokenByPlayer(player);
            return Optional.ofNullable(token.getStats().get(category).get(stat));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public void set(String category, String stat, int value) {
        StatsToken token = statsManager.getStatsTokenByPlayer(player);
        if (token == null) {
            token = new StatsToken(player.getUniqueId());
        }

        if (token.getStats() == null) {
            token.setStats(new HashMap<>());
        }

        if (token.getStats().get(category) == null) {
            token.getStats().put(category, new HashMap<>());
        }

        token.getStats().get(category).put(stat, value);
        statsManager.saveStatsToken(token);
    }

    public void add(String category, String stat, int value) {
        StatsToken token = statsManager.getStatsTokenByPlayer(player);
        if (token == null) {
            token = new StatsToken(player.getUniqueId());
        }

        if (token.getStats() == null) {
            token.setStats(new HashMap<>());
        }

        if (token.getStats().get(category) == null) {
            token.getStats().put(category, new HashMap<>());
        }

        token.getStats().get(category).merge(stat, value, Integer::sum);
        statsManager.saveStatsToken(token);
    }
}