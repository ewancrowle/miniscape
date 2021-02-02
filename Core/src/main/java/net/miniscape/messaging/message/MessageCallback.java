package net.miniscape.messaging.message;

import com.google.inject.Inject;
import net.miniscape.messaging.event.EventCallback;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.PlayerManager;
import net.miniscape.player.Players;
import net.miniscape.translation.Translations;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessageCallback implements EventCallback<MessageData> {
    @Inject
    private PlayerManager playerManager;

    @Override
    public void execute(MessageData data) {
        NetworkPlayer receiver = Players.of(data.getReceiver()).optional().get();
        if (receiver.getBlocked().contains(data.getSender())) {
            return;
        }

        NetworkPlayer sender = Players.of(data.getSender()).optional().get();

        Player player = receiver.getPlayer();
        if (player != null) {
            Translations.to(player).sendMessage("MESSAGE_RECEIVED", sender.getName(), data.getMessage());
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
        }
    }
}