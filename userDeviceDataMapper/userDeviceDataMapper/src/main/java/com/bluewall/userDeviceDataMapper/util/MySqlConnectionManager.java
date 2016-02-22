package com.bluewall.userDeviceDataMapper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionManager implements AutoCloseable {

    private static Logger LOG = LoggerFactory.getLogger(MySqlConnectionManager.class);

    private static Connection CONNECTION;

    /**
     *
     * @return Connection
     */
    public Connection getConnection() {
        LOG.info("Getting mysql connection");
        if (CONNECTION == null) {
            CONNECTION = createConnection();
        }
        return CONNECTION;
    }

    public void close() throws SQLException {
        if (CONNECTION != null) {
            LOG.info("Auto closing mysql connection");
            CONNECTION.close();
            LOG.debug("Mysql connection closed");
        }
    }

    /**
     * @return Connection
     */
    private Connection createConnection() {
        Connection connection;
        LOG.info("Creating mysql connection");
        try {
            Class.forName(Constants.MYSQL_DRIVER);
            connection = DriverManager.getConnection(Constants.MYSQL_RDS_URI, Constants.MYSQL_RDS_USER, Constants.MYSQL_RDS_PASSWORD);
        } catch (ClassNotFoundException e) {
            LOG.info("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            LOG.info("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return connection;
    }
}
