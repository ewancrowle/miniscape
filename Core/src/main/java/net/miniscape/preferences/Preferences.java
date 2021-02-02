package net.miniscape.preferences;

import com.google.inject.Inject;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;

public class Preferences {
    @Inject
    private PreferencesManager preferencesManager;

    private Player player;

    private Preferences(Player player) {
        this.player = player;
    }

    public static Preferences of(Player player) {
        return new Preferences(player);
    }

    public <T> Optional<T> get(PreferenceType preference, Class<T> tClass) {

        try {
            return Optional.ofNullable((T) preferencesManager.getPreferencesTokenByPlayer(player).getPreferences().get(preference));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    public void set(PreferenceType preference, Object object) {
        PreferencesToken token = preferencesManager.getPreferencesTokenByPlayer(player);
        if (token == null) {
            token = new PreferencesToken(player.getUniqueId());
        }

        if (token.getPreferences() == null) {
            token.setPreferences(new HashMap<>());
        }

        token.getPreferences().put(preference, object);
        preferencesManager.savePreferencesToken(token);
    }
}
