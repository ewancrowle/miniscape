package net.miniscape.islandescape.clothing;

import com.google.inject.Inject;

public enum Clothing {
    BLUE_TEE;

    @Inject
    private ClothingManager clothingManager;

    public ClothingData getData() {
        return clothingManager.getClothingData(this);
    }
}
