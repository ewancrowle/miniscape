package net.miniscape.command;

import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.Players;
import net.miniscape.translation.Translations;
import net.miniscape.util.Response;
import net.miniscape.util.guice.Manager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.*;
import java.util.stream.Collectors;

@Manager(name = "Command Manager")
public class CommandManager {
    private Map<String, Class<?>> commands = new HashMap<>();

    public CommandManager() {
        load(getCommands());
    }

    @EventHandler
    public void onPlayerChat(PlayerCommandPreprocessEvent event) throws IllegalAccessException, InstantiationException {
        if (event.isCancelled()) {
            return;
        }

        String message = event.getMessage();
        message = message.substring(1);

        String[] args = message.split("\\W+");
        String cmd = args[0].toLowerCase();
        args = Arrays.copyOfRange(args, 1, args.length);

        Player player = event.getPlayer();

        Class<?> c_class = commands.get(cmd);

        if (c_class == null) {
            Translations.to(player).sendMessage(new Response(false, "COMMAND_NOT_FOUND"));
            event.setCancelled(true);
            return;
        }

        Command command = c_class.getAnnotation(Command.class);
        CommandService service = (CommandService) c_class.newInstance();

        Response response = handleCommand(command, service, player, args);
        if (!response.isSuccess()) {
            if (response.getMessage() != null || response.getMessage() == "") {
                player.sendMessage(ChatColor.RED + response.getMessage());
            }
        }

        event.setCancelled(true);
    }

    public Response handleCommand(Command command, CommandService service, Player player, String[] args) {
        Optional<NetworkPlayer> playerOptional = Players.of(player).optional();
        if (playerOptional.isPresent()) {
            if (command.rank() != null) {
                NetworkPlayer np = playerOptional.get();
                if (!np.exceeds(command.rank())) {
                    return new Response(false, Translations.to(player).get("PERMISSION"));
                }
            }
            return service.execute(player, args);
        }
        return new Response(false, "Missing player data!");
    }

    public Set<Class<?>> getCommands() {
        Reflections reflections = new Reflections("net.miniscape", new TypeAnnotationsScanner());
        return reflections.getTypesAnnotatedWith(Command.class).stream().filter(CommandService.class::isInstance).collect(Collectors.toSet());
    }

    public void load(Set<Class<?>> classes) {
        for (Class<?> c_class : classes) {
            Command command = c_class.getAnnotation(Command.class);
            if (command.enabled()) {
                for (String name : command.names()) {
                    commands.put(name, c_class);
                }
            }
        }
    }
}