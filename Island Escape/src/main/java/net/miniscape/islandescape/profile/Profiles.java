package net.miniscape.islandescape.profile;

import net.miniscape.islandescape.Island;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Profiles {
    private List<Profile> profileList;

    public Profiles(Player player) {
    }

    public Profiles(UUID profileUuid) {
    }

    public static Profiles of(Player player) {
        return new Profiles(player);
    }

    public static Profiles of(UUID profileUuid) {
        return new Profiles(profileUuid);
    }

    public Optional<Profile> optional() {
        return Optional.ofNullable(profileList.get(0));
    }

    public List<Profile> getAll() {
        return profileList;
    }
}
