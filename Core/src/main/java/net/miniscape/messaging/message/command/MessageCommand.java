package net.miniscape.messaging.message.command;

import com.google.inject.Inject;
import net.miniscape.command.Command;
import net.miniscape.command.CommandService;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.Players;
import net.miniscape.translation.Translations;
import net.miniscape.util.Response;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

import java.util.Optional;

@Command(names = {"message", "msg", "tell", "whisper"})
public class MessageCommand implements CommandService {
    @Inject
    private MessagingManager messagingManager;

    @Override
    public Response execute(Player player, String[] args) throws CommandException {
        if (args == null || args.length < 2) {
            return new Response(false, Translations.to(player).get("MESSAGE_USAGE"));
        }

        Optional<NetworkPlayer> targetOptional = Players.of(args[0]).optional();
        if (!targetOptional.isPresent()) {
            return new Response(false, Translations.to(player).get("MESSAGE_FAILED_OFFLINE"));
        }

        NetworkPlayer target = targetOptional.get();

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length - 1; i++) {
            builder.append(args[i]);
        }

        return messagingManager.sendMessage(player, target, builder.toString());
    }
}