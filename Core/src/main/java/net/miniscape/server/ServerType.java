package net.miniscape.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerType {
    HUB("hub"),
    MINI_GAME("game"),
    ISLAND_ESCAPE("ocean");

    private String name;
}