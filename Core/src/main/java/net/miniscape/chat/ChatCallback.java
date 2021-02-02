package net.miniscape.chat;

import com.google.inject.Inject;
import net.miniscape.friend.FriendManager;
import net.miniscape.messaging.event.EventCallback;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.Players;
import net.miniscape.player.Rank;
import net.miniscape.translation.Translations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.Optional;

public class ChatCallback implements EventCallback<ChatData> {
    @Inject
    private FriendManager friendManager;

    @Override
    public void execute(ChatData data) {
        ChatChannel channel = data.getChatChannel();
        if (channel == ChatChannel.LOCAL) {
            return;
        }

        Optional<NetworkPlayer> playerOptional = Players.of(data.getPlayer()).optional();
        if (!playerOptional.isPresent()) {
            return;
        }

        NetworkPlayer np = playerOptional.get();

        switch (channel) {
            case FRIEND: {
                Bukkit.getOnlinePlayers().stream().filter(friend -> friendManager.isFriendship(friend.getUniqueId(), np.getUuid())).forEach(receiver -> {
                    Optional<NetworkPlayer> receiverOptional = Players.of(receiver).optional();
                    if (receiverOptional.isPresent()) {
                        NetworkPlayer r_np = receiverOptional.get();
                        if (!r_np.getBlocked().contains(np.getUuid())) {
                            String msg = data.getMessage();
                            String name = np.getName();

                            if (data.getMessage().contains(name)) {
                                msg.replaceAll(name, ChatColor.YELLOW + name + ChatColor.WHITE);
                                receiver.playSound(receiver.getLocation(), Sound.NOTE_PLING, 1, 2);
                            }

                            String prefix = ChatColor.YELLOW + "[" + Translations.to(receiver).get("FRIEND").toUpperCase() + "]";

                            receiver.sendMessage(String.format("%s %s §7%s: §f%s", prefix, np.getRank().getPrefix(), np.getName(), msg));
                        }
                    }
                });
            }

            case STAFF: {
                Bukkit.getOnlinePlayers().stream().forEach(receiver -> {
                    Optional<NetworkPlayer> receiverOptional = Players.of(receiver).optional();
                    if (receiverOptional.isPresent()) {
                        NetworkPlayer r_np = receiverOptional.get();
                        if (r_np.isStaff()) {
                            if (!r_np.getBlocked().contains(np.getUuid())) {
                                String msg = data.getMessage();
                                String name = np.getName();

                                if (data.getMessage().contains(name)) {
                                    msg.replaceAll(name, ChatColor.YELLOW + name + ChatColor.WHITE);
                                    receiver.playSound(receiver.getLocation(), Sound.NOTE_PLING, 1, 2);
                                }

                                String prefix = ChatColor.DARK_GREEN + "[STAFF]";

                                receiver.sendMessage(String.format("%s %s §7%s: §f%s", prefix, np.getRank().getPrefix(), np.getName(), msg));
                            }
                        }
                    }
                });
            }

            case ADMIN: {
                Bukkit.getOnlinePlayers().stream().forEach(receiver -> {
                    Optional<NetworkPlayer> receiverOptional = Players.of(receiver).optional();
                    if (receiverOptional.isPresent()) {
                        NetworkPlayer r_np = receiverOptional.get();
                        if (r_np.exceeds(Rank.ADMIN)) {
                            if (!r_np.getBlocked().contains(np.getUuid())) {
                                String msg = data.getMessage();
                                String name = np.getName();

                                if (data.getMessage().contains(name)) {
                                    msg.replaceAll(name, ChatColor.YELLOW + name + ChatColor.WHITE);
                                    receiver.playSound(receiver.getLocation(), Sound.NOTE_PLING, 1, 2);
                                }

                                String prefix = ChatColor.RED + "[ADMIN]";

                                receiver.sendMessage(String.format("%s %s §7%s: §f%s", prefix, np.getRank().getPrefix(), np.getName(), msg));
                            }
                        }
                    }
                });
            }
        }
    }
}
