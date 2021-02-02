package net.miniscape.data.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Getter;
import net.miniscape.data.Datasource;
import net.miniscape.util.environment.EnvironmentProvider;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Arrays;

public class MongoDatasourceImpl implements Datasource {
    @Getter
    private MongoClient client;

    @Getter
    private Datastore datastore;

    private String host;
    private int port;

    @Getter
    private MongoCredential credential;
    private String username, password;

    @Getter
    private String database;

    public MongoDatasourceImpl() {
        host = new EnvironmentProvider("MONGO_HOST").get();
        port = Integer.parseInt(new EnvironmentProvider("MONGO_PORT").get());
        username = new EnvironmentProvider("MONGO_USER").get();
        password = new EnvironmentProvider("MONGO_PASS").get();
        database = new EnvironmentProvider("MONGO_DATA").get();
    }

    @Override
    public void openConnection() {
        credential = MongoCredential.createCredential(username, database, password.toCharArray());
        client = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));

        datastore = new Morphia().createDatastore(client, database);
        datastore.ensureIndexes();
    }

    @Override
    public void close() {
        client.close();
    }
}
