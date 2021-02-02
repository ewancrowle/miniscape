package net.miniscape.islandescape.item.furniture;

import lombok.Getter;
import net.miniscape.islandescape.item.BaseItem;
import net.miniscape.islandescape.item.GameItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class FurnitureItem extends BaseItem {
    private List<Entity> physicalList = new ArrayList<>();

    public FurnitureItem(GameItem item, ItemStack inventoryItem, int buyPrice, int sellPrice) {
        super(item, inventoryItem, buyPrice, sellPrice);
    }

    public void onInteract(Player player) {

    }

    public void onDestruct(Player player) {

    }
}