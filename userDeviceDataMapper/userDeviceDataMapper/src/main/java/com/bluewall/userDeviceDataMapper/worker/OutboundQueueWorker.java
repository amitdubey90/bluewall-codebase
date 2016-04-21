package com.bluewall.userDeviceDataMapper.worker;

import com.bluewall.userDeviceDataMapper.bean.ActivityLog;
import com.bluewall.userDeviceDataMapper.queue.QueueManager;
import com.bluewall.userDeviceDataMapper.sql.Queries;
import com.bluewall.userDeviceDataMapper.util.MySqlConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 * Worker thread that reads {@link ActivityLog} from outbound queue and writes them to the database.
 */
@Slf4j
public class OutboundQueueWorker extends Thread {

    private QueueManager<Document> queue;
    private boolean forever;
    private int workerId;

    public OutboundQueueWorker(ThreadGroup tg, QueueManager<Document> queue, int workerId) {
        this.queue = queue;
        this.workerId = workerId;
        this.forever = true;
        this.setName("OutboundWorker-"+workerId);

        if (queue == null) {
            throw new RuntimeException("Queue manager is null");
        }
    }

    @Override
    public void run() {
        log.info("Outbound worker {} running.", workerId);
        try (MySqlConnectionManager sqlConnectionMgr = new MySqlConnectionManager()) {
            Connection connection = sqlConnectionMgr.getConnection();
            PreparedStatement pst = connection.prepareStatement(Queries.ACTIVITY_LOG_INSERT);

            int recordCount = 0;
            while (true) {

                try {
                    if (!forever && queue.getOutboundSize() == 0) {
                        break;
                    }

                    ActivityLog activityLog = queue.dequeueOutbound();

                    if (activityLog != null) {
                        log.info("Outbound {}", activityLog.toString());
                        int colIndex = 1;

                        pst.setInt(colIndex++, activityLog.getUserID());
                        pst.setString(colIndex++, activityLog.getActivityName());
                        pst.setInt(colIndex++, activityLog.getDistance());
                        pst.setTimestamp(colIndex++, activityLog.getStartTime());
                        pst.setLong(colIndex++, activityLog.getDuration());
                        pst.setInt(colIndex++, activityLog.getCaloriesBurnt());
                        pst.setInt(colIndex++, activityLog.getLoggedFrom());

                        pst.addBatch();
                        recordCount++;
                    } else if (recordCount > 0) {
                        flushToDatabase(pst);
                        recordCount = 0;
                    }
                } catch (SQLException e) {
                    log.error("SQLException in outbound queue worker {}", e);
                } catch (Exception e) {
                    log.error("Exception in outbound queue worker {}", e);
                }
            }
            //flush any remaining records before shutting down
            if (recordCount > 0) {
                flushToDatabase(pst);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!forever) {
            log.info("Outbound worker {} shutting down.", workerId);
        }
    }

    /**
     * Executes a batch of {@link PreparedStatement}
     * @param pst
     * @return int - total number of records inserted
     * @throws SQLException
     */
    private int flushToDatabase(PreparedStatement pst) throws SQLException {
        log.info("Flushing records to database.");
        int output[];
        try {
            output = pst.executeBatch();
        } catch (SQLException e) {
            log.error("Failed to execute batch.");
            throw (e);
        }
        int updateCount = IntStream.of(output).sum();
        log.info("Successfully flushed {} records to database", updateCount);
        return updateCount;
    }

    /**
     * Method to shutdown the thread
     */
    public void shutdown() {
        log.info("Outbound worker {} shutting down initiated", workerId);
        forever = false;
    }
}
