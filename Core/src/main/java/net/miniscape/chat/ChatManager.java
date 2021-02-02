package net.miniscape.chat;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.messaging.event.Event;
import net.miniscape.messaging.event.EventType;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.PlayerManager;
import net.miniscape.player.Players;
import net.miniscape.server.ServerManager;
import net.miniscape.util.guice.Manager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;


@Manager(name = "Chat Manager")
public class ChatManager {
    @Inject
    private ServerManager serverManager;

    @Inject
    private MessagingManager messagingManager;

    @Getter
    @Setter
    private ChatChannel channel = ChatChannel.LOCAL;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();

        NetworkPlayer np = Players.of(player).optional().get();

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!np.getBlocked().contains(player.getUniqueId())) {
                String msg = message;
                String name = p.getName();

                if (message.contains(name)) {
                    msg.replaceAll(name, ChatColor.YELLOW + name + ChatColor.WHITE);
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 2);
                }

                p.sendMessage(String.format("%s §7%s§f: %s", np.getRank().getPrefix(), player.getName(), msg));
            }
        }

        sendMessage(player, channel, message);
    }

    public void sendMessage(Player player, ChatChannel channel, String message) {
        long time = System.currentTimeMillis();
        String server = serverManager.getServer().getName();
        ChatData data = new ChatData(server, channel, player.getUniqueId(), message, time);

        messagingManager.getEventPublisher().publish(new Event(EventType.CHAT, data));
    }
}