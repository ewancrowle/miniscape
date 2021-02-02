package net.miniscape.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.miniscape.server.ServerType;

@Getter
@AllArgsConstructor
public enum GameType {
    HUB(ServerType.HUB),
    ISLAND_ESCAPE(ServerType.ISLAND_ESCAPE);

    private ServerType serverType;
}