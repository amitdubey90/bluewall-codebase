package com.bluewall.userDeviceDataMapper.feeder;

import com.bluewall.userDeviceDataMapper.queue.QueueManager;
import com.bluewall.userDeviceDataMapper.util.MongoConnectionManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

/**
 * Feeder implementation with MongoDB as a source.
 */
@Slf4j
public class MongoFeeder extends Thread implements Feeder<Document>  {

    private QueueManager<Document> queueManager;
    private MongoConnectionManager connectionManager;
    private MongoCursor<Document> cursor;

    private boolean forever;
    private long lastID;

    public MongoFeeder(QueueManager<Document> queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public void enqueueToMapperQueue(Document request) {
        queueManager.enqueueInbound(request);
    }

    @Override
    public void startFeeder() {
        connectionManager = new MongoConnectionManager();
        forever = true;
        this.start();
        lastID = getLastProcessedID();
        log.info("Mongo string feeder started!");
    }

    @Override
    public void stopFeeder(boolean hard) {
        if(hard) {
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
        Filters.gt("","");
        //TODO add filter for last doc id


        while (forever) {
            try {
                cursor = collection.find().iterator();
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    log.debug("Receive doc - {}", document.toJson());
                    enqueueToMapperQueue(document);
                }
                sleep(1000);
            } catch (Exception e) {
                log.error("Exception while running feeder. {}", e);
            }

        }
    }

    /**
     * Close cursor
     */
    private void closeCursor() {
        if(cursor != null) {
            cursor.close();
        }
    }

    /**
     * Returns the last ID present in the database
     * @return long
     */
    private long getLastProcessedID() {
        //TODO init last ID
        return 0;
    }
}
