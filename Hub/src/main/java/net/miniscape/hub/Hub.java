package net.miniscape.hub;

import net.miniscape.util.ServerPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

@Plugin(name = "Hub", version = "0.1.0")
public class Hub extends ServerPlugin {
    public Hub() {
        getModuleLoader().addManagers(HubManager.class).load();
    }
}