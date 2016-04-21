import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MySqlConnectionManager {
    private static Connection CONNECTION;

    // MySql Connection Settings
    public static final String MYSQL_RDS_URI = "jdbc:mysql://userdatabase.cjydixq0uvpi.us-west-1.rds.amazonaws.com/userDatabase";
    public static final String MYSQL_RDS_USER = "root";
    public static final String MYSQL_RDS_PASSWORD = "q1w2e3r4t5y6";
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private static Connection createConnection() {
        Connection connection;
        log.info("Creating mysql connection");
        try {
            Class.forName(MYSQL_DRIVER);
            connection = DriverManager.getConnection(MYSQL_RDS_URI, MYSQL_RDS_USER, MYSQL_RDS_PASSWORD);
        } catch (ClassNotFoundException e) {
            log.info("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            log.info("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    /**
     *
     * @return Connection
     */
    public static Connection getConnection() {
        log.info("Getting mysql connection");
        if (CONNECTION == null) {
            CONNECTION = createConnection();
        }
        return CONNECTION;
    }
}
