package net.miniscape.util;

import lombok.Getter;
import net.miniscape.chat.ChatManager;
import net.miniscape.command.CommandManager;
import net.miniscape.connection.ConnectionManager;
import net.miniscape.data.DataManager;
import net.miniscape.friend.FriendManager;
import net.miniscape.messaging.MessagingManager;
import net.miniscape.player.PlayerManager;
import net.miniscape.scoreboard.ScoreboardManager;
import net.miniscape.server.ServerManager;
import net.miniscape.session.SessionManager;
import net.miniscape.stats.StatsManager;
import net.miniscape.translation.TranslationManager;
import net.miniscape.util.guice.ModuleLoader;
import net.miniscape.util.guice.ServerModule;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ServerPlugin extends JavaPlugin {
    private ModuleLoader moduleLoader;

    @Override
    public void onEnable() {
        moduleLoader = new ModuleLoader()
                .addInjectorModules(new ServerModule(this))
                .addManagers(DataManager.class, CommandManager.class, TranslationManager.class, MessagingManager.class)
                .addManagers(ServerManager.class, PlayerManager.class, SessionManager.class, StatsManager.class, ConnectionManager.class)
                .addManagers(ChatManager.class, FriendManager.class, ScoreboardManager.class)
                .load();
    }
}