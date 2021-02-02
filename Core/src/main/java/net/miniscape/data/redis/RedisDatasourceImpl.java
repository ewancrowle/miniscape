package net.miniscape.data.redis;

import lombok.Getter;
import net.miniscape.data.Datasource;
import net.miniscape.util.environment.EnvironmentProvider;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDatasourceImpl implements Datasource {
    @Getter
    private JedisPool pool;
    private JedisPoolConfig poolConfig;

    private String host;
    private int port;
    private String password;

    public RedisDatasourceImpl() {
        host = new EnvironmentProvider("REDIS_HOST").get();
        port = Integer.parseInt(new EnvironmentProvider("REDIS_PORT").get());
        password = new EnvironmentProvider("REDIS_PASS").get();
        poolConfig = new JedisPoolConfig();
    }

    @Override
    public void openConnection() {
        pool = new JedisPool(poolConfig, host, port, 0, password);
    }

    @Override
    public void close() {
        if (pool != null) {
            pool.destroy();
        }
    }
}
