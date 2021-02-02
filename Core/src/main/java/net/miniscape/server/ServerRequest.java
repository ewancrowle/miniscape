package net.miniscape.server;

import lombok.Data;
import net.miniscape.game.GameType;
import net.miniscape.util.environment.Environment;

@Data
public class ServerRequest {
    private final Environment environment;
    private final GameType gameType;
}
