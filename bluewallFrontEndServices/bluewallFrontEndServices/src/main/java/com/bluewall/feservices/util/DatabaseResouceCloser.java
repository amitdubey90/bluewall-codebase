package com.bluewall.feservices.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class DatabaseResouceCloser {
    public static void closeAll(Connection connection, PreparedStatement pst, ResultSet rs)
            throws SQLException {
        closeConnection(connection);
        closePreparedStatement(pst);
        closeResultSet(rs);
    }

    public static void closeAllSilent(Connection connection, PreparedStatement pst, ResultSet rs) {
        try {
            closeConnection(connection);
            closePreparedStatement(pst);
            closeResultSet(rs);
        } catch (SQLException e) {
            // ignore
        }
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("Error closing database connection");
                throw e;
            }
        }
    }

    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Error closing database connection");
                throw e;
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement pst) throws SQLException {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                log.error("Error closing database connection");
                throw e;
            }
        }
    }
}
