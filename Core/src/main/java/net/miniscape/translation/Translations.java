package net.miniscape.translation;

import com.google.inject.Inject;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.Players;
import net.miniscape.util.Response;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class Translations {
    @Inject
    private TranslationManager manager;

    private Player player;

    public Translations(Player player) {
        this.player = player;
    }

    public static Translations to(Player player) {
        return new Translations(player);
    }

    public void sendMessage(String key, Object... args) {
        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        if (playerOptional.isPresent()) {
            NetworkPlayer np = playerOptional.get();
            String translation = manager.get(key, np.getLanguage());
            for (Object o : args) {
                translation = String.format(translation, o);
            }
            player.sendMessage(translation);
        }
    }

    public void sendMessage(Response response) {
        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        if (playerOptional.isPresent()) {
            NetworkPlayer np = playerOptional.get();
            String translation = manager.get(response.getMessage(), np.getLanguage());
            ChatColor color = response.isSuccess() ? ChatColor.GREEN : ChatColor.RED;
            player.sendMessage(color + translation);
        }
    }

    public String get(String key) {
        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        if (playerOptional.isPresent()) {
            NetworkPlayer np = playerOptional.get();
            return manager.get(key, np.getLanguage());
        }
        return manager.get(key, Language.ENGLISH);
    }
}
