package net.miniscape.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean success;
    private String message;

    public static Response success() {
        Response response = new Response();
        return response;
    }

    public void sendMessage(Player player) {
        if (success) {
            player.sendMessage(ChatColor.GREEN + message);
        } else {
            player.sendMessage(ChatColor.RED + message);
        }
    }
}
