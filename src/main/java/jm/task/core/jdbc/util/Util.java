package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    private final Logger logger = Logger.getLogger(Util.class.getName());
    private Connection connection = null;
    private final String url = "jdbc:mysql://localhost:3306/preproject_1";
    private final String user = "root";
    private final String pass = "power100power";

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, pass);
                logger.info(Util.class.getName() + ": create connection");
            } catch (SQLException e) {
                logger.info(Util.class.getName() + "Connect failed");
            }
        }
        return connection;
    }
}
