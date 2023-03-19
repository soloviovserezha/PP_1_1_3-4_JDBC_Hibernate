package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static Connection connection = null;
    private static final String url = "jdbc:mysql://localhost:3306/preproject_1";
    private static final String user = "root";
    private static final String pass = "power100power";

    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, pass);
                logger.info(Util.class.getName() + ": create connection");
            }
        } catch (SQLException e) {
            logger.info(Util.class.getName() + "Connect failed");
        }

        return connection;
    }
}
