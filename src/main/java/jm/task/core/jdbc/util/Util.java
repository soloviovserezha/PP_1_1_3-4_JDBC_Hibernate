package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static Connection connection = null;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/preproject_1";
    private static final String user = "root";
    private static final String pass = "power100power";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Properties properties = new Properties();

            properties.put(Environment.DRIVER, driver);
            properties.put(Environment.URL, url);
            properties.put(Environment.USER, user);
            properties.put(Environment.PASS, pass);

            properties.put(Environment.POOL_SIZE, "10");

            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

            properties.put(Environment.SHOW_SQL, "false");

            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            properties.put(Environment.HBM2DDL_AUTO, "create-drop");

            Configuration configuration = new Configuration();
            configuration.setProperties(properties);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public static Connection getConnection() {
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
