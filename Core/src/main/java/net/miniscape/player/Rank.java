package net.miniscape.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public enum Rank {
    ADMIN(ChatColor.RED, "Admin"),
    DEV(ChatColor.RED, "Developer"),
    MOD(ChatColor.YELLOW, "Mod"),
    HELPER(ChatColor.DARK_AQUA, "Helper"),
    BUILDER(ChatColor.DARK_GREEN, "Builder"),
    CREATOR(ChatColor.GREEN, "Creator"),
    MEMBER_PLUS(ChatColor.GOLD, "Member§f+"),
    MEMBER(ChatColor.GOLD, "Member"),
    NONE(ChatColor.GRAY, "None");

    private final ChatColor color;
    private final String name;

    public String getPrefix() {
        return color + "[" + name + color + "]";
    }
}