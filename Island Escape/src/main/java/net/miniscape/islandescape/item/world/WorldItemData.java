package net.miniscape.islandescape.item.world;

import lombok.Data;
import net.miniscape.islandescape.item.furniture.FurnitureItem;
import org.bukkit.entity.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@org.mongodb.morphia.annotations.Entity(value = "world_item_data", noClassnameStored = true)
public class WorldItemData {
    @Indexed(options = @IndexOptions(unique = true))
    private UUID island;

    private Map<UUID, FurnitureItem> furnitureEntityMap = new HashMap<>();

    public void addEntity(FurnitureItem item, Entity... entities) {
        for (Entity entity : entities) {
            furnitureEntityMap.put(entity.getUniqueId(), item);
        }
    }

    public void removeEntity(Entity... entities) {
        for (Entity entity : entities) {
            furnitureEntityMap.remove(entity.getUniqueId());
        }
    }
}