package net.miniscape.islandescape.clothing;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@Data
public class Outfit {
    private Clothing hat;
    private Clothing shirt;
    private Clothing pants;
    private Clothing shoes;

    public void apply(Player player) {
        if (hat != null) {
            player.getInventory().setHelmet(hat.getData().getItemStack());
        } else {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }

        if (shirt != null) {
            player.getInventory().setHelmet(hat.getData().getItemStack());
        } else {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }

        if (pants != null) {
            player.getInventory().setHelmet(hat.getData().getItemStack());
        } else {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }

        if (shoes != null) {
            player.getInventory().setHelmet(hat.getData().getItemStack());
        } else {
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }
    }
}