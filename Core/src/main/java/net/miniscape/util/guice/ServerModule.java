package net.miniscape.util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import net.miniscape.util.ServerPlugin;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class ServerModule extends AbstractModule {
    private final ServerPlugin plugin;

    public ServerModule(ServerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        this.bindScope(Manager.class, Scopes.SINGLETON);

        this.bind(ServerPlugin.class).toInstance(this.plugin);
        this.bind(Server.class).toProvider(this.plugin::getServer);
        this.bind(PluginManager.class).toProvider(this.plugin.getServer()::getPluginManager);

        this.bindListener(Matchers.any(), new ListenerTypeListener());
    }

    private class ListenerTypeListener implements TypeListener {
        @Override
        public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
            Class<?> clazz = type.getRawType();
            if (Listener.class.isAssignableFrom(clazz)) {
                encounter.register((InjectionListener<I>) obj -> ServerModule.this.plugin.getServer().getPluginManager().registerEvents((Listener) obj, ServerModule.this.plugin));
            }
        }
    }
}
