package net.miniscape.islandescape.item;

import com.google.inject.Inject;
import net.miniscape.islandescape.item.furniture.FurnitureItem;
import net.miniscape.islandescape.item.world.WorldItemData;
import net.miniscape.translation.TranslationManager;
import net.miniscape.util.event.TickEvent;
import net.miniscape.util.guice.Manager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.mongodb.morphia.Datastore;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Manager(name = "Item Manager")
public class ItemManager {
    @Inject
    private TranslationManager translationManager;

    @Inject
    private Datastore datastore;

    public ItemManager() {
        for (GameItem item : GameItem.values()) {
            if (item.getType() == ItemType.FURNITURE) {
            }
        }
    }

    public WorldItemData getWorldItemData(UUID islandId) {
        return datastore.find(WorldItemData.class).field("island").equal(islandId).get();
    }

    @EventHandler
    public void onTick(TickEvent event) {

    }

    @EventHandler
    public void onEntityInteract(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {

        }
    }

    public BaseItem createItem(GameItem item) throws IllegalAccessException, InstantiationException {
        return item.getItemClass().newInstance();
    }

    public Optional<GameItem> getGameItem(ItemStack itemStack) {
        if (itemStack.getData().getItemType() != Material.AIR) {
            String name = itemStack.getItemMeta().getDisplayName();
            Optional<String> optionalKey = getKey(name);
            if (optionalKey.isPresent()) {
                String key = optionalKey.get();
                return Arrays.stream(GameItem.values()).filter(gameItem -> gameItem.getKey() == key).findFirst();
            }
        }

        return Optional.empty();
    }

    public Optional<String> getKey(String name) {
        for (Map<String, String> map : translationManager.getTranslations().values()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals(name)) {
                    return Optional.of(entry.getKey());
                }
            }
        }
        return Optional.empty();
    }
}
