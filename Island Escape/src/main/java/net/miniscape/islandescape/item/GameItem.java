package net.miniscape.islandescape.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.miniscape.islandescape.item.items.RadioItem;

@Getter
@AllArgsConstructor
public enum GameItem {
    RADIO("ITEM_RADIO", ItemType.FURNITURE, RadioItem.class);

    private String key;
    private ItemType type;
    private Class<? extends BaseItem> itemClass;
}