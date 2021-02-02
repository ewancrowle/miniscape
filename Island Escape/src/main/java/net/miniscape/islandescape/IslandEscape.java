package net.miniscape.islandescape;

import net.miniscape.islandescape.item.ItemManager;
import net.miniscape.util.ServerPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

@Plugin(name = "IslandEscape", version = "0.1.0")
public class IslandEscape extends ServerPlugin {
    public IslandEscape() {
        getModuleLoader().addManagers(IslandManager.class, ItemManager.class).load();
    }
}
