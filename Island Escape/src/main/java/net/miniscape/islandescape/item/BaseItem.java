package net.miniscape.islandescape.item;

import lombok.Data;
import net.miniscape.translation.Translations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@Data
public abstract class BaseItem {
    private final GameItem item;

    private final ItemStack inventoryItem;

    private final int buyPrice, sellPrice;

    public void givePlayer(Player player) {
        ItemStack itemStack = new ItemStack(inventoryItem);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(Translations.to(player).get(item.getKey()));
        meta.setLore(Arrays.asList(ChatColor.DARK_GRAY + Translations.to(player).get(item.getKey())));
        itemStack.setItemMeta(meta);

        player.getInventory().addItem(itemStack);
    }

    public void onEntityInteract(Entity entity, Player player) {

    }

    public void onEntityPunched(Entity entity, Player player) {

    }

    public abstract void placeItem(Location location);

    public abstract void removeItem(Location location);
}