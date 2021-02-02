package net.miniscape.islandescape.clothing;

import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.mongodb.morphia.annotations.Id;

@Data
public class ClothingData {
    @Id
    private final Clothing clothing;

    private final String nameKey;
    private final ItemStack itemStack;

    private final int buy, sell;
}
