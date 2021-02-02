package net.miniscape.translation.command;

import net.miniscape.command.Command;
import net.miniscape.command.CommandService;
import net.miniscape.util.Response;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

@Command(names = {"language", "lang", "translations", "locale"})
public class LanguageCommand implements CommandService {
    @Override
    public Response execute(Player player, String[] args) throws CommandException {
        return null;
    }
}
