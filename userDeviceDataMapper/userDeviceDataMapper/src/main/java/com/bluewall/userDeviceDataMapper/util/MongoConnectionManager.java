package com.bluewall.userDeviceDataMapper.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connection manager for MongoDB. This connects to mongo lab on cloud
 */
public class MongoConnectionManager implements AutoCloseable {

    private static Logger LOG = LoggerFactory.getLogger(MongoConnectionManager.class);

    private static MongoClient MONGO_CLIENT;

    /**
     * Creates a new connection if null otherwise returns the same connection in the
     * subsequent calls.
     * @return {@link MongoClient}
     */
    public MongoClient getConnection() {
        if(MONGO_CLIENT == null) {
            LOG.info("Getting client for mongodb");
            MONGO_CLIENT = createConnection();
        }
        return MONGO_CLIENT;
    }

    /**
     * Method to create client for mongodb.
     * @return {@link MongoClient}
     */
    private MongoClient createConnection() {
        LOG.info("Creating client for mongodb");
        return new MongoClient(new MongoClientURI(Constants.MONGO_LAB_URI));
    }

    public void close() throws Exception {
        LOG.info("Closing mongo client connection");
        MONGO_CLIENT.close();
    }
}
