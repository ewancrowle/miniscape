package net.miniscape.islandescape.clothing;

import com.google.inject.Inject;
import net.miniscape.util.guice.Manager;
import org.mongodb.morphia.Datastore;

@Manager(name = "Clothing Manager")
public class ClothingManager {
    @Inject
    private Datastore datastore;

    public ClothingData getClothingData(Clothing clothing) {
        return null;
    }
}
