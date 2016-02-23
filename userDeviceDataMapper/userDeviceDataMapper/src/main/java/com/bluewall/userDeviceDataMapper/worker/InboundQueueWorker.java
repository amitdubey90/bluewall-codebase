package com.bluewall.userDeviceDataMapper.worker;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import com.bluewall.userDeviceDataMapper.mappper.ActivityLogMapper;
import com.bluewall.userDeviceDataMapper.mappper.DocumentMapperFactory;
import com.bluewall.userDeviceDataMapper.mappper.FitbitDocumentMapper;
import com.bluewall.userDeviceDataMapper.queue.QueueManager;
import com.bluewall.userDeviceDataMapper.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Worker thread that reads from Inbound mapper queue, transforms the
 * document to {@link ActivityLog} and enqueues it to outbound queue
 */
@Slf4j
public class InboundQueueWorker extends Thread {

    private QueueManager<Document> queue;
    private boolean forever;
    private int workerId;

    public InboundQueueWorker(ThreadGroup tg, QueueManager<Document> queue, int workerId) {
        this.queue = queue;
        this.workerId = workerId;
        this.forever = true;

        if (queue == null) {
            throw new RuntimeException("Queue manager is null");
        }
    }


    @Override
    public void run() {
        log.info("Inbound worker {} running.", workerId);
        while (true) {

            try {
                if (!forever && queue.getInboundSize() == 0) {
                    break;
                }

                Document input = queue.dequeueInbound();
                if (input != null) {
                    ActivityLogMapper<Document> mapper = DocumentMapperFactory
                            .getActivityMapper(input.getString(Constants.DEVICE_TYPE_KEY));
                    if(mapper == null) {
                        log.debug("Could not find a mapper for input {}", input.toJson());
                    } else {
                        ActivityLog response = mapper.map(input);
                        log.debug("Enqueuing to out queue {}", response);
                        queue.enqueueOutbound(response);
                    }
                }

            } catch (Exception e) {
                log.error("Unknown exception in inbound worker {}", e);
                e.printStackTrace();
            }
        }

        if (!forever) {
            log.info("Inbound worker {} shutting down.", workerId);
        }
    }

    /**
     * Method to shutdown the thread
     */
    public void shutdown() {
        log.info("Inbound worker {} shutting down initiated.", workerId);
        forever = false;
    }
}
