package net.miniscape.hub;

import net.miniscape.player.NetworkPlayer;
import net.miniscape.player.Players;
import net.miniscape.util.guice.Manager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

@Manager(name = "Hub Manager")
public class HubManager {
    private World world;

    public HubManager() {
        world = Bukkit.getServer().getWorld("world");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(world.getSpawnLocation());
    }
}