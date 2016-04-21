package com.bluewall.userDeviceDataMapper.feeder;

import com.bluewall.userDeviceDataMapper.queue.QueueManager;
import com.bluewall.userDeviceDataMapper.sql.Queries;
import com.bluewall.userDeviceDataMapper.util.Constants;
import com.bluewall.userDeviceDataMapper.util.MongoConnectionManager;
import com.bluewall.userDeviceDataMapper.util.MySqlConnectionManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Feeder implementation with MongoDB as a source.
 */
@Slf4j
public class MongoFeeder extends Thread implements Feeder<Document> {

    private QueueManager<Document> queueManager;
    private MongoConnectionManager connectionManager;
    private MongoCursor<Document> cursor;

    private boolean forever;
    private boolean lastIDInitialized;
    private long lastID;

    public MongoFeeder(QueueManager<Document> queueManager) {
        this.queueManager = queueManager;
        this.setName("MongoFeeder");
    }

    @Override
    public void enqueueToMapperQueue(Document request) {
        queueManager.enqueueInbound(request);
    }

    @Override
    public void startFeeder() {
        connectionManager = new MongoConnectionManager();
        forever = true;
        initializeLastID();
        this.start();
        log.info("Mongo string feeder started!");
    }

    @Override
    public void stopFeeder(boolean hard) {
        log.info("Mongo feeder shut down started");
        if (hard) {
            closeCursor();
        }
        forever = false;
        try {
            connectionManager.close();
        } catch (Exception e) {
            log.error("Exception closing mongo connection {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        MongoClient mongoClient = connectionManager.getConnection();
        MongoDatabase db = mongoClient.getDatabase("user-activity-raw");
        MongoCollection<Document> collection = db.getCollection("user-activity-raw_collection");

        while (forever) {
            try {
                if (lastIDInitialized) {
                    cursor = collection.find(Filters.gt("doc_id", lastID)).iterator();
                    while (cursor.hasNext()) {
                        Document document = cursor.next();
                        log.debug("Receive doc - {}", document.toJson());
                        enqueueToMapperQueue(document);
                        lastID = document.getInteger(Constants.DOC_ID_KEY);
                    }
                } else {
                    initializeLastID();
                }
                //sleep for a minute
                sleep(60 * 1000);
            } catch (Exception e) {
                log.error("Exception while running feeder. {}", e);
            }
        }
        closeCursor();
        log.info("Feeder shut down complete!");
    }

    /**
     * Close cursor
     */
    private void closeCursor() {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * Initializes lastId
     */
    private void initializeLastID() {
        lastID = getLastProcessedID();
    }

    /**
     * Returns the last ID present in the database inserted through
     * the mapper module
     *
     * @return long
     */
    public long getLastProcessedID() {
        log.info("Fetching last id from database");
        try {
            MySqlConnectionManager sqlConnectionMgr = new MySqlConnectionManager();
            Connection connection = sqlConnectionMgr.getConnection();
            PreparedStatement pst = connection.prepareStatement(Queries.MAX_LOG_ID_FROM_DEVICE);

            ResultSet rs = pst.executeQuery();
            rs.next();
            int lastId = rs.getInt("maxId");
            log.info("Last Id retrieved : {}", lastId);

            lastIDInitialized = true;

            return lastId;
        } catch (SQLException e) {
            log.error("Error in getting last id. ", e);
            e.printStackTrace();
        }
        return 0;
    }
}
