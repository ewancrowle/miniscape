package net.miniscape.data;

import com.google.inject.Inject;
import com.google.inject.Injector;
import net.miniscape.data.mongo.MongoDatasourceImpl;
import net.miniscape.data.redis.RedisDatasourceImpl;
import net.miniscape.util.guice.Manager;
import org.bukkit.event.EventHandler;

@Manager(name = "Data Manager")
public class DataManager {
    private RedisDatasourceImpl redis;
    private MongoDatasourceImpl mongo;

    @Inject
    private Injector injector;

    public DataManager() {
        redis = new RedisDatasourceImpl();
        mongo = new MongoDatasourceImpl();

        mongo.openConnection();
        redis.openConnection();

        injector.injectMembers(mongo.getDatastore());
        injector.injectMembers(redis.getPool());
    }
}