package net.miniscape.server;

import lombok.Getter;
import lombok.Setter;
import net.miniscape.game.GameType;
import net.miniscape.util.environment.Environment;
import net.miniscape.util.environment.EnvironmentProvider;
import net.miniscape.util.guice.Manager;

@Manager(name = "Server Manager")
public class ServerManager {
    @Getter
    @Setter
    private Server server;

    public ServerManager() {
        String name = new EnvironmentProvider("POD_NAME").get();
        String address = new EnvironmentProvider("POD_IP").get();
        GameType gameType = GameType.valueOf(new EnvironmentProvider("GAME_TYPE").get());
        Environment environment = Environment.valueOf(new EnvironmentProvider("SERVER_ENV").get());
        server = new Server(name, address, gameType, environment);
    }
}