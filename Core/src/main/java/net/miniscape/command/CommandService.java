package net.miniscape.command;

import net.miniscape.util.Response;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;

public interface CommandService {
    Response execute(Player player, String[] args) throws CommandException;
}