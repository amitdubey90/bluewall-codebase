package com.bluewall.userDeviceDataMapper.main;

import com.bluewall.userDeviceDataMapper.feeder.Feeder;
import com.bluewall.userDeviceDataMapper.feeder.MongoFeeder;
import com.bluewall.userDeviceDataMapper.queue.QueueManager;
import com.bluewall.userDeviceDataMapper.queue.DocumentQueueManager;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class Run {
    public static void main(String[] args) throws InterruptedException {
        QueueManager<Document> q = new DocumentQueueManager();

        Feeder<Document> feeder = new MongoFeeder(q);
        feeder.startFeeder();
        Thread.sleep(10000);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Beginning shutdown");
                feeder.stopFeeder(false);
                q.shutdown(false);
            }
        });

    }
}
