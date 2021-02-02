package net.miniscape.islandescape;

import net.miniscape.islandescape.profile.Profile;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Islands {
    private List<Island> islandList;

    public Islands(Profile profile) {
    }

    public Islands(Player player) {
    }

    public Islands(UUID uuid) {
    }

    public static Islands of(Profile profile) {
        return new Islands(profile);
    }

    public static Islands of(Player player) {
        return new Islands(player);
    }

    public static Islands of(UUID profileUuid) {
        return new Islands(profileUuid);
    }

    public Optional<Island> optional() {
        return Optional.ofNullable(islandList.get(0));
    }

    public List<Island> getAll() {
        return islandList;
    }
}
