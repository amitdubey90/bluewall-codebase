package com.bluewall.userDeviceDataMapper.queue;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import com.bluewall.userDeviceDataMapper.worker.InboundQueueWorker;
import com.bluewall.userDeviceDataMapper.worker.OutboundQueueWorker;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * {@link Document} based implementation for {@link QueueManager}
 */
@Slf4j
public class DocumentQueueManager implements QueueManager<Document> {

    private LinkedBlockingDeque<Document> inbound;
    private LinkedBlockingDeque<ActivityLog> outbound;
    private InboundQueueWorker inboundWorker;
    private OutboundQueueWorker outboundWorker;

    public DocumentQueueManager() {
        inbound = new LinkedBlockingDeque<>();
        inboundWorker = new InboundQueueWorker(null, this, 1);

        outbound = new LinkedBlockingDeque<>();
        outboundWorker = new OutboundQueueWorker(null, this, 1);
        init();
    }

    /**
     * Starts all workers
     */
    private void init() {
        log.info("Initializing Queue Manager");

        inboundWorker.start();

        outboundWorker.start();

        log.info("Queue manager initialized!");
    }

    @Override
    public void shutdown(boolean hard) {
        log.info("Queue manager is shutting down");

        if (hard) {
            inbound.clear();
        }

        if (inboundWorker != null) {
            inboundWorker.shutdown();
        }

        if (outboundWorker != null) {
            outboundWorker.shutdown();
        }

        log.info("Queue manager shut down complete");
    }

    @Override
    public void enqueueInbound(Document request) {
        try {
            if (request != null) {
                inbound.put(request);
            }
        } catch (InterruptedException e) {
            log.error("Could not enqueue on inbound", e);
        }
    }

    @Override
    public Document dequeueInbound() {
        try {
            return inbound.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.debug("Nothing dequeue from inbound", e);
        }
        return null;
    }

    @Override
    public void enqueueOutbound(ActivityLog request) {
        try {
            if (request != null) {
                outbound.put(request);
            }
        } catch (InterruptedException e) {
            log.error("Could not enqueue on outbound", e);
        }
    }

    @Override
    public ActivityLog dequeueOutbound() {
        try {
            return outbound.poll(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.debug("Nothing to dequeue from outbound", e);
        }
        return null;
    }

    @Override
    public int getInboundSize() {
        return inbound.size();
    }

    @Override
    public int getOutboundSize() {
        return outbound.size();
    }
}
