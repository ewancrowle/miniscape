package net.miniscape.server.command;

import net.miniscape.server.ServerRequest;
import net.miniscape.util.environment.Environment;

public class StageCommand {
    // creates and sends the player to a new staging server
    // /stage LOBBY
    // use the environment flag -d to create a dev server, or directly specify staging with -s (pointless)
    // /stage LOBBY -d

    public String request(ServerRequest request) {
        return null;
    }

    public Environment parseFlag(String flag) {
        switch (flag.toUpperCase()) {
            case "-D":
                return Environment.DEV;
            default:
                return Environment.STAGING;
        }
    }
}